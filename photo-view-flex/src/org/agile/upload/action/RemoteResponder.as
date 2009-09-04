package org.agile.upload.action {
    import mx.controls.Alert;
    import mx.core.IFlexDisplayObject;
    import mx.managers.CursorManager;
    import mx.managers.PopUpManager;
    import mx.rpc.IResponder;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class RemoteResponder implements IResponder {

        private var _loadingBar:IFlexDisplayObject;

        private var _callback:Function;

        private var _onResult:Function;

        private var _onFault:Function;

        public function set loadingBar(obj:IFlexDisplayObject):void {
            this._loadingBar=obj;
        }

        public function RemoteResponder(callback:Function, onResult:Function, onFault:Function) {
            this._callback=callback;
            this._onResult=onResult;
            this._onFault=onFault;
        }


        public function fault(info:Object):void {
            beforeFault(info);
            trace(info);
            Alert.show("> " + info, "RPC Call Error!");
            afterFault(info);
        }


        public function result(data:Object):void {
            beforeResult(data);
            if (_onResult != null) {
                var evt:ResultEvent=data as ResultEvent;
                _onResult(evt.token.result);
            }
            if (_callback != null) {
                _callback();
            }
        }

        /**
         * 在responder响应fault事件前调用
         * */
        private function beforeFault(info:Object):void {
            if (this._loadingBar) {
                PopUpManager.removePopUp(this._loadingBar);
            }
            CursorManager.removeBusyCursor();
        }

        /**
         * 在responder响应fault事件后调用
         * */
        private function afterFault(info:Object):void {
            var event:FaultEvent=info as FaultEvent;

            if (event.fault.faultCode == "com.chinacache.oss.exception.BusinessValidateException" && _onFault != null) {
                _onFault(event);
                return;
            }

            //RemoteObject调用出错，弹出错误消息框。
            // var errBox:ErrorBox=ErrorBox(PopUpManagerFactory.getPopUpManager().createPopUp(DisplayObject(Application.application), ErrorBox, true));

            //设置错误消息框显示信息
            // errBox.errorTrace=event.fault.faultDetail;
            // errBox.title="错误";
            // errBox.errorText=event.fault.faultString;
            // errBox.showErrorTrace=true;

            //判断异常是否为session失效异常
            //var sessionExpiredException:Boolean=false;
            //if (event.fault.faultString == "session.expired") {
            //    errBox.errorText="会话已失效，请重新登陆！";
            //    sessionExpiredException=true;
            //}


            //增加监听器，监听消息框关闭事件。
            //errBox.addEventListener("close", function(event:Event):void {
            //会话已失效，退出登录
            //        if (sessionExpiredException) {
            //            Application.application.reload();
            //        }
            //         PopUpManager.removePopUp(errBox);


            //     });

            //使消息框居中
            //PopUpManager.centerPopUp(errBox);
            // errBox.setFocus();

        }


        /**
         * 在responder响应result事件之前调用
         * */
        private function beforeResult(data:Object):void {
            if (this._loadingBar) {
                PopUpManager.removePopUp(this._loadingBar);
            }
            CursorManager.removeBusyCursor();

        }

        public function set callback(callback:Function):void {
            this._callback=callback;
        }

        public function set onResult(onResult:Function):void {
            this._onResult=onResult;
        }
    }
}