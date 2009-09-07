package org.agile.upload.logging {

    import flash.utils.describeType;

    import mx.collections.ArrayCollection;
    import mx.controls.TextArea;
    import mx.logging.ILogger;
    import mx.logging.Log;
    import mx.logging.LogEventLevel;
    import mx.logging.targets.LineFormattedTarget;
    import mx.logging.targets.TraceTarget;

    /**
     * The custom logger factory of eSoftHead company
     */
    public class LoggerFactory {
        private static var targets:ArrayCollection=new ArrayCollection();
        private static var instance:LoggerFactory=new LoggerFactory();

        function LoggerFactory() {
            if (instance != null) {
                throw new Error("The instance Logger already be exist");
            }
            var traceTarget:TraceTarget=new TraceTarget();
            traceTarget.filters=["org.agile.*"];
            traceTarget.level=LogEventLevel.ALL;
            traceTarget.includeCategory=true;
            traceTarget.includeDate=true;
            traceTarget.includeLevel=true;
            traceTarget.includeTime=true;
            // Log.addTarget(traceTarget);
            // targets.addItem(traceTarget);
        }

        public static function setLoggingArea(loggingArea:TextArea):void {
            var textareaTarget:TextLogTarget=new TextLogTarget(loggingArea);
            textareaTarget.filters=["org.agile.*"];
            Log.addTarget(textareaTarget);
            targets.addItem(textareaTarget);
        }

        private function getInternalLogByClazz(clz:Class):ILogger {
            var type:XML=describeType(clz);
            var className:String=type.@name;
            var category:String=className.replace("::", ".");
            var log:ILogger=Log.getLogger(category);
            loginLogger(log);
            return log;
        }

        private function getInternalLogByString(str:String):ILogger {
            var log:ILogger=Log.getLogger(str);
            loginLogger(log);
            return log;
        }

        private function loginLogger(log:ILogger):void {
            for (var i:int, len:int=targets.length; i < len; i++) {
                var target:LineFormattedTarget=targets.getItemAt(i) as LineFormattedTarget;
                target.addLogger(log);
            }
        }

        public static function getLogger(obj:Object):ILogger {
            if (obj is Class) {
                return instance.getInternalLogByClazz(obj as Class);
            } else if (obj is String) {
                return instance.getInternalLogByString(obj as String);
            }
            return Log.getLogger("default");
        }
    }
}