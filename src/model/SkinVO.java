package model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SkinVO {

	private String skinName;
	private String skinChamp;
	private int skinPrice;
	private String skinRegister;
	private String skinImage1;
	private String skinImage2;
	private String skinImage3;
	private ImageView imageView;
	private ReadOnlyObjectWrapper<Image> image;
	private String userId;

	private int count;
	
	
	public SkinVO(String skinName, String skinChamp, int skinPrice, String skinRegister, String skinImage1,
			String skinImage2, String skinImage3) {
		super();
		this.skinName = skinName;
		this.skinChamp = skinChamp;
		this.skinPrice = skinPrice;
		this.skinRegister = skinRegister;
		this.skinImage1 = skinImage1;
		this.skinImage2 = skinImage2;
		this.skinImage3 = skinImage3;
	}

	public SkinVO(Image image, String skinName, String skinChamp, int skinPrice, String skinRegister) {
		super();
		this.image = new ReadOnlyObjectWrapper<>(image);
		this.skinName = skinName;
		this.skinChamp = skinChamp;
		this.skinPrice = skinPrice;
		this.skinRegister = skinRegister;
	}
	
	public SkinVO(int skinPrice, String skinImage1, String skinImage2, String skinName) {

		super();
		this.skinPrice = skinPrice;
		this.skinName = skinName;
		this.skinImage1 = skinImage1;
		this.skinImage2 = skinImage2;
	}
	
	public SkinVO(String skinName, String skinChamp, int skinPrice, String skinRegister) {
		super();
		this.skinName = skinName;
		this.skinChamp = skinChamp;
		this.skinPrice = skinPrice;
		this.skinRegister = skinRegister;
	}

	public SkinVO(String skinChamp, int skinPrice, String skinRegister) {
		super();
		this.skinChamp = skinChamp;
		this.skinPrice = skinPrice;
		this.skinRegister = skinRegister;
	}
	
	public SkinVO(String skinChamp, int count) {
		super();
		this.skinChamp = skinChamp;
		this.count = count;
	}

	public SkinVO(String skinChamp) {
		super();
		this.skinChamp = skinChamp;
	}
	
	public ImageView getImageView() {
		return imageView;
	}


	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

    public Image getImage() {
        return image.get();
    }

    public ReadOnlyObjectProperty<Image> imageProperty() {
        return image.getReadOnlyProperty();
    }
	

	public String getSkinName() {
		return skinName;
	}

	public void setSkinName(String skinName) {
		this.skinName = skinName;
	}

	public String getSkinChamp() {
		return skinChamp;
	}

	public void setSkinChamp(String skinChamp) {
		this.skinChamp = skinChamp;
	}

	public int getSkinPrice() {
		return skinPrice;
	}

	public void setSkinPrice(int skinPrice) {
		this.skinPrice = skinPrice;
	}

	public String getSkinRegister() {
		return skinRegister;
	}

	public void setSkinRegister(String skinRegister) {
		this.skinRegister = skinRegister;
	}

	public String getSkinImage1() {
		return skinImage1;
	}

	public void setSkinImage1(String skinImage1) {
		this.skinImage1 = skinImage1;
	}

	public String getSkinImage2() {
		return skinImage2;
	}

	public void setSkinImage2(String skinImage2) {
		this.skinImage2 = skinImage2;
	}

	public String getSkinImage3() {
		return skinImage3;
	}

	public void setSkinImage3(String skinImage3) {
		this.skinImage3 = skinImage3;
	}
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
