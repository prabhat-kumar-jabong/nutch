package org.apache.nutch.namespace;

import java.util.LinkedList;
import java.util.List;

public class ParserFilterBuilder {
	
	 /** Parsing was not performed. */
	  public static final byte NOTFILTERED = 0;
	  /** Parsing succeeded. */
	  public static final byte SUCCESS = 1;
	  /** General failure. There may be a more specific error message in arguments. */
	  public static final byte FAILED = 2;

	  public static final String[] majorCodes = { "notfiltered", "success", "failed" };

	
	public static ParserFilterStatus build(){
		ParserFilterStatus pfs =  new ParserFilterStatus();
		pfs.args = new LinkedList<java.lang.CharSequence>();
		return pfs;
	}
	
	public static class ParserFilterStatus{
		
		byte status = NOTFILTERED;
		private java.util.List<java.lang.CharSequence> args;
		
		
		public void setStatus(byte status){
			this.status = status;
		}
		
		public List<CharSequence> getArgs(){
			return this.args;
		}
		
	}

}
