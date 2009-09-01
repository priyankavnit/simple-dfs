package org.agile.upload.view {
    import flash.events.MouseEvent;
    import flash.filters.ColorMatrixFilter;

    import mx.controls.Button;

    public class FilterButton extends Button {

        private var _singleClickListener:Function;

        public function FilterButton() {
            super();
        }

        override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
            super.updateDisplayList(unscaledWidth, unscaledHeight);
            if (!enabled) {
                filters=[new ColorMatrixFilter([0.3086, 0.6094, 0.0820, 0, 0, 0.3086, 0.6094, 0.0820, 0, 0, 0.3086, 0.6094, 0.0820, 0, 0, 0, 0, 0, 1, 0])];
            } else {
                filters=[];
            }
        }

        public function set singleClickListener(listener:Function):void {
            if (_singleClickListener != null) {
                super.removeEventListener(MouseEvent.CLICK, _singleClickListener);
            }
            if (listener != null) {
                super.addEventListener(MouseEvent.CLICK, listener);
                _singleClickListener=listener;
            }
        }
    }
}