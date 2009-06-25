package org.agile.framework.util {
    import mx.formatters.DateFormatter;
    
    public class DateUtil { 
        public static function format(ms:Number,format:String):String{
    	    var date:Date = new Date(ms);
    	    var dft:DateFormatter  = new DateFormatter();
    	    dft.formatString = format;
    	    var time:String  = dft.format(date);    
    	    return time;        
        }

     public static function getRoundSecond(ms:Number):uint{
    	    var date:Date = new Date(ms);
    	    var s:uint = date.getSeconds(); 
    	    var m:uint = date.getMilliseconds()
    	    return m > 500 ? s + 1 : s - 1;        
        }
        
    }
}