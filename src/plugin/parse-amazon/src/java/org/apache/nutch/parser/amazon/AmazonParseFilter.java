/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.nutch.parser.amazon;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.avro.util.Utf8;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.metadata.Metadata;
import org.apache.nutch.namespace.ParseFilter;
import org.apache.nutch.namespace.ParserFilterBuilder;
import org.apache.nutch.namespace.ParserFilterBuilder.ParserFilterStatus;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.util.Bytes;
import org.apache.nutch.util.EncodingDetector;
import org.apache.nutch.util.TableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;
import org.xml.sax.InputSource;

public class AmazonParseFilter implements ParseFilter {
  public static final Logger LOG = LoggerFactory
      .getLogger("org.apache.nutch.amazon.filter");
  
//NUTCH-1006 Meta equiv with single quotes not accepted
 private static Pattern metaPattern = Pattern.compile(
     "<meta\\s+([^>]*http-equiv=(\"|')?content-type(\"|')?[^>]*)>",
     Pattern.CASE_INSENSITIVE);
 private static Pattern charsetPattern = Pattern.compile(
     "charset=\\s*([a-z][_\\-0-9a-z]*)", Pattern.CASE_INSENSITIVE);
 private static Pattern charsetPatternHTML5 = Pattern.compile(
     "<meta\\s+charset\\s*=\\s*[\"']?([a-z][_\\-0-9a-z]*)[^>]*>",
     Pattern.CASE_INSENSITIVE);

  private Configuration conf;

  private String defaultCharEncoding;
  
  private static final int CHUNK_SIZE = 2000;
  
  public void setConf(Configuration conf) {
    this.conf = conf;
    this.defaultCharEncoding = getConf().get(
            "parser.character.encoding.default", "windows-1252");
  }

  public Configuration getConf() {
    return this.conf;
  }

  public static void main(String[] args) throws Exception {

  }

	@Override
  public ParserFilterStatus filter(String url, WebPage page) {
		
		String baseUrl = TableUtil.toString(page.getBaseUrl());
	    URL base;
	    try {
	      base = new URL(baseUrl);
	    } catch (MalformedURLException e) {
	      return null;
	    }

	    // parse the content
	    try {
	      ByteBuffer contentInOctets = page.getContent();
	      InputSource input = new InputSource(new ByteArrayInputStream(
	          contentInOctets.array(), contentInOctets.arrayOffset()
	              + contentInOctets.position(), contentInOctets.remaining()));

	      EncodingDetector detector = new EncodingDetector(conf);
	      detector.autoDetectClues(page, true);
	      detector.addClue(sniffCharacterEncoding(contentInOctets), "sniffed");
	      String encoding = detector.guessEncoding(page, defaultCharEncoding);

	      page.getMetadata().put(new Utf8(Metadata.ORIGINAL_CHAR_ENCODING),
	          ByteBuffer.wrap(Bytes.toBytes(encoding)));
	      page.getMetadata().put(new Utf8(Metadata.CHAR_ENCODING_FOR_CONVERSION),
	          ByteBuffer.wrap(Bytes.toBytes(encoding)));

	      input.setEncoding(encoding);
	      if (LOG.isTraceEnabled()) {
	        LOG.trace("Parsing...");
	      }
	      
	      
	      Tidy tidy = new Tidy();
	        org.w3c.dom.Document root = tidy.parseDOM(input.getByteStream(), null);;
			String xpathExpression = "//*[@id=\"wayfinding-breadcrumbs_feature_div\"]/ul/li[7]/span/a";
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) xPath.evaluate(xpathExpression,root.getDocumentElement(), XPathConstants.NODESET);
			List<String> valueList = new LinkedList<String>();
			for (int i = 0; i < nodes.getLength(); ++i) {
				valueList.add(nodes.item(i).getChildNodes().item(0).getNodeValue());
				valueList.add(nodes.item(i).getTextContent());
			}
	      
	      
	    } catch (DOMException e) {
	        LOG.error("Failed with the following DOMException: ", e);
	        return getEmptyFilter(e);
	      } catch (Exception e) {
	        LOG.error("Failed with the following Exception: ", e);
	        return getEmptyFilter(e);
	      }
		
		return getSuccessFilter();
  }
	
	
	 public static ParserFilterStatus getEmptyFilter(Exception e) {
		    ParserFilterStatus status = ParserFilterBuilder.build();
		    status.setStatus(ParserFilterBuilder.FAILED);
		    status.getArgs().add(new Utf8(e.toString()));
		    return status;
     }
	 
	 public static ParserFilterStatus getSuccessFilter() {
		    ParserFilterStatus status = ParserFilterBuilder.build();
		    status.setStatus(ParserFilterBuilder.SUCCESS);
		    return status;
  }

	
	  /**
	   * Given a <code>ByteBuffer</code> representing an html file of an
	   * <em>unknown</em> encoding, read out 'charset' parameter in the meta tag
	   * from the first <code>CHUNK_SIZE</code> bytes. If there's no meta tag for
	   * Content-Type or no charset is specified, the content is checked for a
	   * Unicode Byte Order Mark (BOM). This will also cover non-byte oriented
	   * character encodings (UTF-16 only). If no character set can be determined,
	   * <code>null</code> is returned. <br />
	   * See also
	   * http://www.w3.org/International/questions/qa-html-encoding-declarations,
	   * http://www.w3.org/TR/2011/WD-html5-diff-20110405/#character-encoding, and
	   * http://www.w3.org/TR/REC-xml/#sec-guessing <br />
	   * 
	   * @param content
	   *          <code>ByteBuffer</code> representation of an html file
	   */

	  private static String sniffCharacterEncoding(ByteBuffer content) {
	    int length = Math.min(content.remaining(), CHUNK_SIZE);

	    // We don't care about non-ASCII parts so that it's sufficient
	    // to just inflate each byte to a 16-bit value by padding.
	    // For instance, the sequence {0x41, 0x82, 0xb7} will be turned into
	    // {U+0041, U+0082, U+00B7}.
	    String str = "";
	    try {
	      str = new String(content.array(), content.arrayOffset()
	          + content.position(), length, Charset.forName("ASCII").toString());
	    } catch (UnsupportedEncodingException e) {
	      // code should never come here, but just in case...
	      return null;
	    }

	    Matcher metaMatcher = metaPattern.matcher(str);
	    String encoding = null;
	    if (metaMatcher.find()) {
	      Matcher charsetMatcher = charsetPattern.matcher(metaMatcher.group(1));
	      if (charsetMatcher.find())
	        encoding = new String(charsetMatcher.group(1));
	    }
	    if (encoding == null) {
	      // check for HTML5 meta charset
	      metaMatcher = charsetPatternHTML5.matcher(str);
	      if (metaMatcher.find()) {
	        encoding = new String(metaMatcher.group(1));
	      }
	    }
	    if (encoding == null) {
	      // check for BOM
	      if (length >= 3 && content.get(0) == (byte) 0xEF
	          && content.get(1) == (byte) 0xBB && content.get(2) == (byte) 0xBF) {
	        encoding = "UTF-8";
	      } else if (length >= 2) {
	        if (content.get(0) == (byte) 0xFF && content.get(1) == (byte) 0xFE) {
	          encoding = "UTF-16LE";
	        } else if (content.get(0) == (byte) 0xFE
	            && content.get(1) == (byte) 0xFF) {
	          encoding = "UTF-16BE";
	        }
	      }
	    }

	    return encoding;
	  }

}
