import java.util.Date;

public class Venit {
    private float valoare;
    private String categorie;
    private Date data;

    public float getValoare() {
        return valoare;
    }

    public void setValoare(float valoare) {
        this.valoare = valoare;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Venit(float valoare, String categorie, Date data) {
        this.valoare = valoare;
        this.categorie = categorie;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Venit{" +
                "valoare=" + valoare +
                ", categorie='" + categorie + '\'' +
                ", data=" + data +
                '}';
    }
}
