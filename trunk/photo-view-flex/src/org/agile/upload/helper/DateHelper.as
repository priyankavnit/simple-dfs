package org.agile.upload.helper {
    import mx.formatters.DateFormatter;

    public class DateHelper {
        private static var dft:DateFormatter=new DateFormatter();

        public static function format(obj:Object, format:String):String {
            var date:Date;
            if (obj is Number) {
                date=new Date(obj as Number);
            } else {
                date=obj as Date;
            }
            dft.formatString=format;
            var time:String=dft.format(date);
            return time;
        }

        public static function getRoundSecond(ms:Number):uint {
            var date:Date=new Date(ms);
            var s:uint=date.getSeconds();
            var m:uint=date.getMilliseconds()
            return m > 500 ? s + 1 : s - 1;
        }

    }
}