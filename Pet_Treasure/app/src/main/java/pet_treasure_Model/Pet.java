package pet_treasure_Model;

public class Pet {

    private int image;
    private String name;
    private String age;
    private String bread;
    private String color;
    private Float rating;
    private boolean forAdoption;
    private byte[] img;


    public Pet() {
    }

    public Pet(int image, String name, String age, String bread, String color, Float rating, boolean forAdoption) {
        this.image = image;
        this.name = name;
        this.age = age;
        this.bread = bread;
        this.color = color;
        this.rating = rating;
        this.forAdoption = forAdoption;
    }

    public Pet(int image1, String mercury) {

        this.image = image1;
        this.name = mercury;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBread() {
        return bread;
    }

    public void setBread(String bread) {
        this.bread = bread;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getForAdoption() {
        return forAdoption;
    }

    public void setForAdoption(Boolean forAdoption) {
        this.forAdoption = forAdoption;
    }


    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }


}
