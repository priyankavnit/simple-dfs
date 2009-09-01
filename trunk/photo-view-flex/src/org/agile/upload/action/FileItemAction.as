package org.agile.upload.action {

    public class FileItemAction extends BaseUploadAction {
        private static var service:String="FileItemManager";

        public function list(onResult:Function=null):void {
            invoke(service, "hello", "some", onResult);
        }

    }
}