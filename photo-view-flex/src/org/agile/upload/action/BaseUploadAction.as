package org.agile.upload.action {

    public class BaseUploadAction extends AbstractAction {

        public function invoke(service:String, method:String, args:Object, onResult:Function):void {
            var url:String=null;
            doInvoke(url, service, method, args, onResult);
        }

    }
}