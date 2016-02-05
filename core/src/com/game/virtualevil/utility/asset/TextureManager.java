package com.game.virtualevil.utility.asset;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureManager {

	private Map<String, TextureRegion> texRegions = new HashMap<String, TextureRegion>();
	
	private final String path = "images/";
	private final String ext = ".png";
	
	public TextureManager() {
		String[] imgNames = {
				"crosshair1",
				"crosshair3",
				"HPETileSet",
				"hero",
				"cyber_tileset",
				"bat",
				"startScreenBackground",
				"chipBuildings",
				"mainMenuButtons"
		};

		for (int i = 0; i < imgNames.length; i++) {
			loadTexture(imgNames[i]);
		}
		// fix bleeding for some tilesets
		fixBleeding(texRegions.get("cyber_tileset"));
		fixBleeding(texRegions.get("chipBuildings"));
	}

	@SuppressWarnings("null")
	public void loadTexture(String fname) {
		TextureRegion textureRegion = new TextureRegion(new Texture(path + fname + ext));
		/*try {
			texture = new Texture(path + fname + ext);
		} catch (GdxRuntimeException e) {
			JOptionPane.showMessageDialog(null, "Error: \n" + path + fname + ext + "\nmissing!", "Error loading image!",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}*/
		
		if (textureRegion != null) {
			texRegions.put(fname, textureRegion);
		}
	}

	public TextureRegion getImage(String s) {
		if (!texRegions.containsKey(s)) {
			// throw error
			System.out.println(" Image not found in hashmap. key=\"" + s + "\"");
		}
		return texRegions.get(s);
	}
	
	public void disposeAllTextures() {
		for (TextureRegion tex : texRegions.values()) {
			tex.getTexture().dispose();
		}
	}
	
	// fix tilemap bleeding; thanks to awilki01 and Hollowbit for the solution
	private static void fixBleeding(TextureRegion region) {
		float fix = 0.01f;
		float x = region.getRegionX();
		float y = region.getRegionY();
		float width = region.getRegionWidth();
		float height = region.getRegionHeight();
		float invTexWidth = 1f / region.getTexture().getWidth();
		float invTexHeight = 1f / region.getTexture().getHeight();
		region.setRegion((x + fix) * invTexWidth, (y + fix) * invTexHeight,
				(x + width - fix) * invTexWidth, (y + height - fix) * invTexHeight);
	}
}
