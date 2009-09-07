package org.agile.upload.helper {

    public class UnitHelper {
        public static var BYTE4MBYTE:int=1024 * 1024;
        public static var MS2SEC:int=1 * 1000;
        public static var SEC2NANO:Number=1000 * 1000 * 1000;

        public static function formatBytes(num:Number):String {
            var k:uint=num / 1024;
            var b:uint=num % 1024;
            var m:uint=k / 1024;
            k=k % 1024;
            var res:String="";
            if (m > 0) {
                res+=m + "M";
            }
            if (k > 0) {
                res+=" " + k + "K";
            }
            if (b > 0) {
                res+=" " + b + "B";
            }
            return res;
        }

        public static function byteToMByte(size:Number):Number {
            var t:int=size / BYTE4MBYTE;
            return t;
        }

        public static function msToSecond(ms:Number):Number {
            var t:int=ms / MS2SEC;
            return t;
        }

        public static function msToTenSecond(ms:Number):Number {
            var t:int=ms / (10 * MS2SEC);
            return t;
        }

        public static function nanoToSecond(nano:Number):Number {
            var t:int=nano / SEC2NANO;
            return t;
        }

        public static function toPercent(num:Number):Number {
            return Math.round(num * 100);
        }
    }
}