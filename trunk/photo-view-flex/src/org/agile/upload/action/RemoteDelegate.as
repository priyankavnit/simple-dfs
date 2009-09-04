package org.agile.upload.action {

    import mx.messaging.ChannelSet;
    import mx.messaging.channels.AMFChannel;
    import mx.rpc.AsyncToken;
    import mx.rpc.remoting.mxml.Operation;
    import mx.rpc.remoting.mxml.RemoteObject;

    public class RemoteDelegate {

        protected var remoteResponder:RemoteResponder;

        /**
         * Constructor
         * @param responder ，实现mx.rpc.IResponder接口的Cairngorm Command对象
         * */
        public function RemoteDelegate(callback:Function=null, onResult:Function=null, onFault:Function=null) {
            remoteResponder=new RemoteResponder(callback, onResult, onFault);
        }

        /**
         * 调用远程方法
         * @param remoteObjectName Services.mxml中配置的RemoteObject的id值
         * @param operationName 要调用的方法名称
         * @param args 被调用方法所需的参数.
         * 		如果调用的远程方法无参数，则可忽略或输入null.
         * 		如果远程方法参数多于一个，则以Array形式组织参数，例：invoke(remoteObjectName,operationName,[arg1,arg2...]);
         * @param showLoadingBar 是否需要显示动态进度栏
         * @param loadingBarContent 动态进度栏的显示内容
         * @param timeout 超时时间（以秒计）
         * */
        public function invoke(url:String, remoteObjectName:String, operationName:String, args:Object=null, showLoadingBar:Boolean=false, loadingBarContent:String=null, timeout:Number=5):void {
            var ro:RemoteObject=new RemoteObject(remoteObjectName);
            if (url != null) {
                var endpoint:String=url;
                var chan:AMFChannel=new AMFChannel("amfOnHttp", endpoint);
                var channelSet:ChannelSet=new ChannelSet();
                channelSet.addChannel(chan);
                ro.channelSet=channelSet;
            }
            ro.requestTimeout=timeout;
            ro.showBusyCursor=true;
            var op:Operation=ro.getOperation(operationName) as Operation;

            if (showLoadingBar) {
                if (!loadingBarContent || loadingBarContent == '')
                    loadingBarContent='处理中......';

                    // var loadingBar:LoadingBar=LoadingBar(PopUpManagerFactory.getPopUpManager().createPopUp(DisplayObject(Application.application), LoadingBar, true));
                    // loadingBar.noticeLabel.text=loadingBarContent;
                    // loadingBar.x=Application.application.width - loadingBar.width;
                    // loadingBar.y=0;
                    // remoteResponder.loadingBar=loadingBar;
            }

            // CursorManager.setBusyCursor();

            var orgiArguments:Object=op.arguments;
            if (args) {
                op.arguments=(args is Array) ? args : [args];
            }

            var call:AsyncToken=op.send();
            call.addResponder(remoteResponder);

            op.arguments=orgiArguments;
        }
    }
}