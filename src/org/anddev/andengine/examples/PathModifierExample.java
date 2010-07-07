package org.anddev.andengine.examples;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.shape.modifier.IShapeModifier;
import org.anddev.andengine.entity.shape.modifier.PathModifier;
import org.anddev.andengine.entity.shape.modifier.IShapeModifier.IShapeModifierListener;
import org.anddev.andengine.entity.shape.modifier.PathModifier.IPathModifierListener;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.util.Path;

import android.widget.Toast;

/**
 * @author Nicolas Gramlich
 * @since 11:54:51 - 03.04.2010
 */
public class PathModifierExample extends BaseExample {
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
	private TextureRegion mFaceTextureRegion;

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
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera, false));
	}

	@Override
	public void onLoadResources() {
		this.mTexture = new Texture(64, 32, TextureOptions.BILINEAR);
		this.mFaceTextureRegion = TextureRegionFactory.createFromAsset(this.mTexture, this, "gfx/boxface.png", 0, 0);

		this.mEngine.getTextureManager().loadTexture(this.mTexture);
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerPreFrameHandler(new FPSLogger());

		final Scene scene = new Scene(1);
		scene.setBackgroundColor(0.09804f, 0.6274f, 0.8784f);

		final int x = (CAMERA_WIDTH - this.mFaceTextureRegion.getWidth()) / 2;
		final int y = (CAMERA_HEIGHT - this.mFaceTextureRegion.getHeight()) / 2;
		final Sprite face = new Sprite(x, y, this.mFaceTextureRegion);

		final Path path = new Path(7).to(x, y).to(100, 100).to(100, 200).to(200, 200).to(200, 100).to(100, 100).to(x, y);
		face.addShapeModifier(new PathModifier(20, path, new IShapeModifierListener() {
			@Override
			public void onModifierFinished(final IShapeModifier pShapeModifier, final IShape pShape) {
				PathModifierExample.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(PathModifierExample.this, "Path finished!", Toast.LENGTH_SHORT).show();
					}
				});
			}
		},
		new IPathModifierListener() {
			@Override
			public void onWaypointPassed(final PathModifier pPathModifier, final IShape pShape, final int pWaypointIndex) {
				PathModifierExample.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(PathModifierExample.this, "Waypoint '" + pWaypointIndex + "' passed...", Toast.LENGTH_SHORT).show();
					}
				});
			}
		}));
		scene.getTopLayer().addEntity(face);

		return scene;
	}

	@Override
	public void onLoadComplete() {

	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
