package org.agile.upload.action {

    public class AbstractAction {

        public function doInvoke(url:String, service:String, method:String, args:Object, onResult:Function):void {
            new RemoteDelegate(null, onResult).invoke(url, service, method, args);

        }

    }
}