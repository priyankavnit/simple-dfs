package org.agile.upload.action {

    public class FileItemAction extends BaseUploadAction {
        private static var service:String="FileItemManager";

        public function hello(onResult:Function=null):void {
            invoke(service, "hello", "some", onResult);
        }

        public function list(onResult:Function=null):void {
            invoke(service, "list", null, onResult);
        }

        public function findById(id:Number, onResult:Function=null):void {
            invoke(service, "findById", id, onResult);
        }
    }
}