package mpp.vlad_dani.common.domain;


public class Client extends BaseEntity<Integer>{
    //attributes
    private String name;
    //
    public Client(){}

    /**
     * Constructor with parameters
     * @param id
     * @param name
     */
    public Client(Integer id, String name){
        super.setId(id);
        this.name=name;
    }

    /**
     * gets the id of the client
     * @return id
     */
    public Integer getId(){return super.getId();}

    /**
     * sets the id of the client with a given id
     * @param id
     */
    public void setId(Integer id){super.setId(id);}

    public String getName(){return this.name;}
    public void setName(String name){this.name=name;}

    /**
     * Constructs a visual representation of the client
     * @return a string representation of the client
     */
    public String toString(){
        return "ID: "+super.getId().toString()+", Name: "+name;
    }

    /**
     * stores a string representation of the client in a StringBuilder to allow storing it in a file
     * @return
     */
    public String toFile(){
        StringBuilder str= new StringBuilder(super.getId().toString() + "," + name);
        return str.toString();
    }
}
