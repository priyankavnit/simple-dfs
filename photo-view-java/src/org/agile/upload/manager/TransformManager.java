package org.agile.upload.manager;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

public class TransformManager {
	private static final ImagesService imagesService = ImagesServiceFactory.getImagesService();

	public TransformManager() {
		// Image oldImage = ImagesServiceFactory.makeImage(oldImageData);
		// Transform resize = ImagesServiceFactory.makeResize(200, 300);
		//
		// Image newImage = imagesService.applyTransform(resize, oldImage);
		//
		// byte[] newImageData = newImage.getImageData();
	}

	public byte[] thumbnail(byte[] old, int width, int height) {
		Image oldImg = ImagesServiceFactory.makeImage(old);
		Transform resize = ImagesServiceFactory.makeResize(width, height);
		// int origWidth = origImg.getWidth();
		// int origHeight = origImg.getHeight();
		// if (width / origWidth > height / origHeight) {
		//
		// } else {
		//
		// }
		Image newImg = imagesService.applyTransform(resize, oldImg);
		return newImg.getImageData();
	}
}
