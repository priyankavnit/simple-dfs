package org.agile.framework.view{
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.formatters.DateFormatter;
	import mx.utils.ObjectUtil;

	public class ExtendColumn extends DataGridColumn{ 
		public function ExtendColumn(columnName:String = null){
	        super(columnName);
			labelFunction = parseLable;
	    	var field:String = column.dataField as String;
	    }
	    
	    /**
	    private function parseGraph(item:Object,field:String):Object{
	    	}  
    		if(field.indexOf(".") < 0){
    		}else{
    			return parseGraph(item[parentKey],childKey);
    		}  
	    }
	}