package classes;

public enum Currence {

    EUR("EUR"),
    PLN("PLN"),
    GBP("GBP"),
    USD("USD"),
    CZK("CZK");

    private String currence;

    Currence(String currence) {

        this.currence = currence;
    }

    public String getCurrence() {
        return currence;
    }
}
