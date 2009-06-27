package org.agile.framework.view{ 
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.formatters.DateFormatter;
	import mx.utils.ObjectUtil;

	public class ExtendColumn extends DataGridColumn{ 	    private static var DATA_TYPE_DATE:String = "date";	    // private static var DATA_TYPE_GRAPH:String = "graph";	    
		public function ExtendColumn(columnName:String = null){
	        super(columnName);
			labelFunction = parseLable;			this.sortCompareFunction = mySortCompareFunction;			this.sortable = false;	    }	    	    public function parseLable(item:Object, column:ExtendColumn):String{
	    	var field:String = column.dataField as String;	    	var target:Object = null;	    	if(field!=null && field.indexOf(".")>0){	    		target = parseGraph(item,field);	    	} else{	    		target = item[field] ;	    	}	    	return format(target,column.dataType,column.dataFormat);
	    }	    	    private function mySortCompareFunction(obj1:Object, obj2:Object):int{	    	var value1:String = parseLable(obj1,this);	    	var value2:String = parseLable(obj2,this);	    	return ObjectUtil.stringCompare(value1,value2);	    }
	    
	    /**	     *该方法将通过递归调用来解析 DataGridColumn 中的dataField部分	     *如果该部分为a.b.c将依次来进行解析,直到取得最后的c的toString()值为止	     */
	    private function parseGraph(item:Object,field:String):Object{	    	if(item == null || field == null) {	    	    return "";
	    	}  
    		if(field.indexOf(".") < 0){    			var val:Object = item[field]     			return val; 
    		}else{    			var parentKey:String = field.substring(0,field.indexOf("."));    			var childKey:String = field.substring(field.indexOf(".")+1,field.length);
    			return parseGraph(item[parentKey],childKey);
    		}  
	    }	    private function format(obj:Object,type:String=null,fmt:String=null):String{	    	if(obj == null){	    		return "null";	    	}	    	if(type == "date"){	    	    return formatDate(obj,fmt);	    	}	    		    	return obj.toString();	    }	    	    private function formatDate(value:Object,fmt:String):String {  	        var data:Date;	        if(value is Date){	            data = value as Date;	        } else if(value is String){	            data = new Date(new Number(value as String));	        } else {	            data = value==null?null:new Date(new Number(value.toString()));	        }	        if(data !=null){    			var formatter:DateFormatter =  new DateFormatter();    			if(fmt != null) {    				formatter.formatString = fmt;    			} else {    				formatter.formatString = "YYYY-MM-DD JJ:NN:SS";    			}     			return formatter.format(data as Date).toString();	        } else {	            return "null";	        }	    }	    	    public var dataType:String;	    public var dataFormat:String; 	    
	}}
