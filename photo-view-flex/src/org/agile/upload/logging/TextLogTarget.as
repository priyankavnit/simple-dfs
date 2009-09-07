package org.agile.upload.logging {
    import mx.controls.TextArea;
    import mx.core.mx_internal;
    import mx.logging.targets.LineFormattedTarget;
    import mx.logging.LogEventLevel;

    use namespace mx_internal;

    public class TextLogTarget extends LineFormattedTarget {

        private var _textArea:TextArea;

        public function TextLogTarget(area:TextArea) {
            super();
            this.level=LogEventLevel.INFO;
            this.includeDate=true;
            this.includeLevel=true;
            this.includeTime=true;
            _textArea=area;
        }

        mx_internal override function internalLog(message:String):void {
            _textArea.text+=message + "\n";
        }

    }
}