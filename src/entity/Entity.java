package entity;

/**
 * Interfata folosita pentru generalizarea codului
 * @author alex
 *
 */
public interface Entity {

    /**
     * Forteaza entitatile sa aiba un id si este folosit la scriere in fisier
     * @return  Intoarce id-ul entitatii
     */
    public Integer getId();

    /**
     * Forteaza entitatile sa aiba un id si este folosit la citire in fisier
     * @param id    Id-ul ce va fi asignat entitatii
     */
    public void setId(Integer id);

}
