package be.nascom.flash.net.upload {
    import flash.display.BitmapData;
    import flash.events.Event;
    import flash.events.EventDispatcher;
    import flash.events.IOErrorEvent;
    import flash.events.SecurityErrorEvent;
    import flash.net.URLLoader;
    import flash.net.URLLoaderDataFormat;
    import flash.net.URLRequest;
    import flash.net.URLRequestHeader;
    import flash.net.URLRequestMethod;
    import flash.utils.ByteArray;

    import mx.graphics.codec.JPEGEncoder;

    // import com.adobe.images.JPGEncoder;

    /**
     * ImageUploader takes a BitmapData object and uploads it to a server using a serverside upload script.
     * Currently, only JPG filetypes are possible, and the filename is defined by the serverside script.
     * The serverside script returns the filename of the uploaded file.
     *
     * <p>TO DO:</p>
     * <ul>
     * <li>Support different filetypes</li>
     * <li>Add clientside filename definition</li>
     * </ul>
     *
     * @see UploadPostHelper
     * @see flash.display.BitmapData
     * @see be.nascom.events.FileEvent
     *
     * @author David Lenaerts
     * @mail david.lenaerts@nascom.be
     *
     */

    public class ImageUploader extends EventDispatcher {
        private var _uploadScriptUrl:String;

        /**
         * Indicates that the uploaded file needs to use JPG compression.
         */
        public static const FILETYPE_JPG:String="jpg";

        /**
         * Creates an ImageUploader instance.
         *
         * @param uploadScriptUrl The URL where the serverside upload script can be found.
         */
        public function ImageUploader(uploadScriptUrl:String):void {
            _uploadScriptUrl=uploadScriptUrl;
        }

        /**
         * Uploads a bitmap to the server.
         *
         * @param source A BitmapData object which will be uploaded to the server.
         * @param quality The quality of the JPG compression. Higher values will result in bigger file sizes.
         * @param fileType Specifies the compression method and file type used for the saved image.
         *
         */
        public function upload(source:BitmapData, quality:uint=75, fileType:String="jpg", uploadDataFieldName:String="filedata", parameters:Object=null):void {
            var encoderJPG:JPEGEncoder=new JPEGEncoder(quality);
            var imageData:ByteArray=encoderJPG.encode(source);
            var urlRequest:URLRequest=new URLRequest();
            urlRequest.url=_uploadScriptUrl;
            urlRequest.contentType='multipart/form-data; boundary=' + UploadPostHelper.getBoundary();
            urlRequest.method=URLRequestMethod.POST;
            urlRequest.data=UploadPostHelper.getPostData("", imageData, uploadDataFieldName, parameters);
            urlRequest.requestHeaders.push(new URLRequestHeader('Cache-Control', 'no-cache'));

            var urlLoader:URLLoader=new URLLoader();
            urlLoader.dataFormat=URLLoaderDataFormat.BINARY;
            urlLoader.addEventListener(Event.COMPLETE, handleComplete);
            urlLoader.addEventListener(IOErrorEvent.IO_ERROR, onError);
            urlLoader.addEventListener(SecurityErrorEvent.SECURITY_ERROR, onSecurityError);
            urlLoader.load(urlRequest);
        }

        private function handleComplete(event:Event):void {
            // dispatchEvent(new FileEvent(FileEvent.FILE_UPLOADED, String(event.target.data)));
            dispatchEvent(event);
        }

        private function onError(event:IOErrorEvent):void {
            dispatchEvent(event);
        }

        private function onSecurityError(event:SecurityErrorEvent):void {
            dispatchEvent(event);
        }
    }
}