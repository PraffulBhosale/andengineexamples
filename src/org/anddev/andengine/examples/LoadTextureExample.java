package org.anddev.andengine.examples;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.util.MathUtils;

import android.view.MotionEvent;
import android.widget.Toast;

/**
 * @author Nicolas Gramlich
 * @since 12:14:29 - 30.06.2010
 */
public class LoadTextureExample extends BaseExample {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================

	private Camera mCamera;
	private Texture mTexture;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public Engine onLoadEngine() {
		Toast.makeText(this, "Touch the screen to load a completely new Texture with every touch!", Toast.LENGTH_LONG).show();
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera, false));
	}

	@Override
	public void onLoadResources() {
		/* Nothing done here. */
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerPreFrameHandler(new FPSLogger());

		final Scene scene = new Scene(1);
		scene.setBackgroundColor(0.09804f, 0.6274f, 0.8784f);

		scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			@Override
			public boolean onSceneTouchEvent(final Scene pScene, final MotionEvent pSceneMotionEvent) {
				if(pSceneMotionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					LoadTextureExample.this.loadNewTexture();
				}

				return true;
			}
		});

		return scene;
	}

	@Override
	public void onLoadComplete() {

	}

	// ===========================================================
	// Methods
	// ===========================================================

	private void loadNewTexture() {
		this.mTexture  = new Texture(32, 32, TextureOptions.BILINEAR);
		final TextureRegion faceTextureRegion = TextureRegionFactory.createFromAsset(this.mTexture, this, "gfx/boxface.png", 0, 0);

		this.mEngine.getTextureManager().loadTexture(this.mTexture);

		final float x = (CAMERA_WIDTH - faceTextureRegion.getWidth()) * MathUtils.RANDOM.nextFloat();
		final float y = (CAMERA_HEIGHT - faceTextureRegion.getHeight()) * MathUtils.RANDOM.nextFloat();
		final Sprite clickToUnload = new Sprite(x, y, faceTextureRegion);
		this.mEngine.getScene().getTopLayer().addEntity(clickToUnload);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}