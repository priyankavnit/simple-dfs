package com.ImageManipulation
{
	import de.popforge.imageprocessing.filters.color.*;
	import de.popforge.imageprocessing.filters.convolution.*;
	import de.popforge.imageprocessing.filters.render.*;
	import de.popforge.imageprocessing.filters.noise.*;
	import de.popforge.imageprocessing.filters.distortion.*;
	import de.popforge.imageprocessing.filters.morphological.*;
	import de.popforge.imageprocessing.core.*;
	import flash.display.BitmapData;
	import flash.display.Bitmap;
	import flash.utils.ByteArray;
	
	public class ImageFilters
	{
		private var bmp:BitmapData = null;
		private var image:Image;
		private var histrogram:Histogram;
		
		public function ImageFilters(bmp:BitmapData)
		{
			this.bmp = bmp;
		}
		
		public function MakeBW():BitmapData 
		{
			image = new Image(bmp.width, bmp.height, ImageFormat.GRAYSCALE);
			//image.grayscale = = new GrayscaleRMY();
			
			image.loadBitmapData(bmp, true);
			
			return image.bitmapData;
		}
		
		public function MakeTile():BitmapData 
		{
			var filter: Tile = new Tile();
			image = new Image(bmp.width, bmp.height, ImageFormat.RGB);
			
			image.loadBitmapData(bmp, true);
			filter.apply( image );
			
			return image.bitmapData;
		}
		
		public function MakePixelate():BitmapData 
		{
			var filter: Pixelate = new Pixelate();
			image = new Image(bmp.width, bmp.height, ImageFormat.RGB);
			
			image.loadBitmapData(bmp, true);
			filter.apply( image );
			
			return image.bitmapData;
		}
		
		public function MakeSepia():BitmapData
		{
			var filter: Sepia = new Sepia();
			image = new Image(bmp.width, bmp.height, ImageFormat.RGB);
			
			image.loadBitmapData(bmp, true);
			filter.apply( image );
			
			return image.bitmapData;
		}
		
		public function CorrectBrightNess(bright:Number, contrast:Number):BitmapData
		{
			//var filter: ContrastCorrection = new ContrastCorrection( 1.2 );
			//var filter: Extract = new Extract( 0x000000, true );
			var filter: BrightnessCorrection = new BrightnessCorrection( bright, true );
			var filter1: ContrastCorrection = new ContrastCorrection( contrast );
			image = new Image(bmp.width, bmp.height, ImageFormat.RGB);
			
			image.loadBitmapData(bmp, true);
			filter.apply( image );
			filter1.apply( image );
			image.updateHistogram();
			return image.bitmapData;
		}
		
		public function GetHistrogram():BitmapData {
			histrogram = image.histogram;
			return histrogram.getBitmapData(image.width, image.height, false);
		}
		
		public function EmbossImage():BitmapData
		{
			var filter: Emboss = new Emboss();
			image = new Image(bmp.width, bmp.height, ImageFormat.RGB);
			
			image.loadBitmapData(bmp, true);
			filter.apply( image );
			
			return image.bitmapData;
		}
		
		public function InfraredImage():BitmapData
		{
			var filter: Infrared = new Infrared();
			image = new Image(bmp.width, bmp.height, ImageFormat.RGB);
			
			image.loadBitmapData(bmp, true);
			filter.apply( image );
			
			return image.bitmapData;
		}
		
		public function OutLineImage():BitmapData
		{
			var filter: Outline = new Outline();
			image = new Image(bmp.width, bmp.height, ImageFormat.RGB);
			
			image.loadBitmapData(bmp, true);
			filter.apply( image );
			
			return image.bitmapData;
		}
		
		public function RedEyeImage():BitmapData
		{
			var filter: RedEyeRemoval = new RedEyeRemoval(70, 1);
			image = new Image(bmp.width, bmp.height, ImageFormat.RGB);
			
			image.loadBitmapData(bmp, true);
			filter.apply( image );
			
			return image.bitmapData;
		}
		
		
		public function TwirlImage(x:Number, y:Number, ang:Number, rad:Number):BitmapData
		{
			var filter: Twirl = new Twirl(x, y, ang, rad);
			image = new Image(bmp.width, bmp.height, ImageFormat.RGB);
			
			image.loadBitmapData(bmp, true);
			filter.apply( image );
			
			return image.bitmapData;
		}
		
		public function saveImage():ByteArray {
			image = new Image(bmp.width, bmp.height, ImageFormat.RGB);
			image.loadBitmapData(bmp, true);
			
			return image.toJPEG(60);
		}
	}
}