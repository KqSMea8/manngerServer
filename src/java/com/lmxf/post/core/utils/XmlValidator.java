package com.lmxf.post.core.utils;
public class XmlValidator {
   private String plat_code;
   private String trade_code;
   
   private  int vaildField(String xml, String parent, String field){
	    String xmlbuf;
	    if(xml==null)   return -1;
	    if(field==null)  return -1;
	    xmlbuf=xml;
	    if(parent!=null) {
	    	String node1="<"+parent+">";
	    	String node2="</"+parent+">";
	    	int pos1=xml.indexOf(node1);
	    	if(pos1<0) return -1;
	    	int pos2=xml.indexOf(node2,pos1);
	    	if(pos2<0) return -1;
	    	xmlbuf=xml.substring(pos1+node1.length(), pos2);
	    }
	 	String node1="<"+field+">";
    	String node2="</"+field+">";
    	int pos1=xmlbuf.indexOf(node1);
    	if(pos1<0) return -1;
    	int pos2=xmlbuf.indexOf(node2,pos1);
    	if(pos2<0) return -1;
  	    return 0;
   }
   public static String getField(String xml, String name){
	   String node1="<"+name+">";
     	String node2="</"+name+">";
     	int pos1=xml.indexOf(node1);
     	if(pos1<0) return null;
   	    int pos2=xml.indexOf(node2,pos1);
   	    if(pos2<0) return null;
	   return xml.substring(pos1+node1.length(),pos2);
   }
   private  int validXml(String xml){
	   int ret;
	   ret=vaildField(xml, null, "ZMSG");
	   if(ret!=0)  return  GConstent.Xml_No_Field_ZMSG;
	   ret=vaildField(xml,  "ZMSG","ZHEAD");
	   if(ret!=0)  return  GConstent.Xml_No_Field_ZHEAD;
	   ret=vaildField(xml,  "ZHEAD","bcode");
	   if(ret!=0)  return  GConstent.Xml_No_Field_bcode;
	   ret=vaildField(xml,  "ZHEAD","tcode");
	   if(ret!=0)  return  GConstent.Xml_No_Field_tcode;
	   ret=vaildField(xml,  "ZHEAD","istart");
	   if(ret!=0)  return  GConstent.Xml_No_Field_istart;
	   ret=vaildField(xml,  "ZHEAD","iend");
	   if(ret!=0)  return  GConstent.Xml_No_Field_iend;
	   ret=vaildField(xml,  "ZHEAD","iflag");
	   if(ret!=0)  return  GConstent.Xml_No_Field_iflag;
	   ret=vaildField(xml, "ZMSG","ZBODY");
	   if(ret!=0)  return  GConstent.Xml_No_Field_ZBODY;
	   ret=vaildField(xml, "ZBODY","auth_name");
	   if(ret!=0)  return  GConstent.Xml_No_Field_auth_name;
	   ret=vaildField(xml, "ZBODY","auth_id");
	   if(ret!=0)  return  GConstent.Xml_No_Field_auth_id;
	   return 0;
   }
   
   public int checkXml(String auth_name, String auth_id, String xml1){
	   String xml=xml1;
	   int ret=validXml(xml);
	   if(ret!=0)  return ret;
	   String plat_code_temp=getField(xml,"bcode");
	   String trade_code_temp=getField(xml,"tcode");
	   plat_code=plat_code_temp;
	   trade_code=trade_code_temp;
	   String auth_name_temp=getField(xml,"auth_name");
	   String auth_id_temp=getField(xml,"auth_id");
	   if(!auth_name_temp.equals(auth_name))  return  GConstent.Auth_Name_No_Same;
	   if(!auth_id_temp.equals(auth_id))  return  GConstent.Auth_Id_No_Same;
	   return 0;
   }
   
   public int checkXmlSite(String auth_name, String auth_id, String xml1){
	   String xml=xml1;
	   int ret=validXml(xml);
	   if(ret!=0)  return ret;
	   String plat_code_temp=getField(xml,"bcode");
	   String trade_code_temp=getField(xml,"tcode");
	   plat_code=plat_code_temp;
	   trade_code=trade_code_temp;
	   String auth_name_temp=getField(xml,"auth_name");
	   String auth_id_temp=getField(xml,"auth_id");
	   if(auth_name==null || auth_name_temp==null || auth_id==null || auth_id_temp==null ||  
	  "".equals(auth_name.trim())  || "".equals(auth_id.trim()) || "".equals(auth_name_temp.trim())  || "".equals(auth_id_temp.trim()))  
		   return  GConstent.Auth_Id_No_Same;
	   return 0;
   }
    
public String getPlat_code() {
	return plat_code;
}
public String getTrade_code() {
	return trade_code;
}
  
}
