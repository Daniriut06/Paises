package entidades;

public class Ciudad {

    private String codigo;
    private String nombre;
    private boolean capitalRegion;
    private boolean capitalPais;
    private int area;
    private int poblacion;


    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isCapitalRegion() {
        return capitalRegion;
    }
    public void setCapitalRegion(boolean capitalRegion) {
        this.capitalRegion = capitalRegion;
    }

    public boolean isCapitalPais() {
        return capitalPais;
    }
    public void setCapitalPais(boolean capitalPais) {
        this.capitalPais = capitalPais;
    }

    public int getArea() {
        return area;
    }
    public void setArea(int area) {
        this.area = area;
    }
    
    public int getPoblacion() {
        return poblacion;
    }
    public void setPoblacion(int poblacion) {
        this.poblacion = poblacion;
    }


}
