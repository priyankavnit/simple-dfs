package org.agile.upload.action {
    import mx.collections.ArrayCollection;

    import org.agile.upload.entity.FileItem;


    public class FileItemAction extends BaseUploadAction {
        private static var service:String="FileItemManager";

        public function save(item:FileItem, onResult:Function=null):void {
            invoke(service, "save", item, onResult);
        }

        public function list(onResult:Function=null):void {
            invoke(service, "list", null, onResult);
        }

        public function remove(ids:ArrayCollection, onResult:Function=null):void {
            invoke(service, "remove", ids, onResult);
        }

        public function findById(id:Number, onResult:Function=null):void {
            invoke(service, "findById", id, onResult);
        }
    }
}