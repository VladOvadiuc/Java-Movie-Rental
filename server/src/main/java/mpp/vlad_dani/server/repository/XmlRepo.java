package mpp.vlad_dani.server.repository;

import mpp.vlad_dani.common.domain.BaseEntity;
import mpp.vlad_dani.common.domain.Client;
import mpp.vlad_dani.common.domain.Movie;
import mpp.vlad_dani.common.domain.Rental;
import mpp.vlad_dani.common.domain.validator.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;
import java.util.Optional;

import static mpp.vlad_dani.server.repository.XmlService.loadClientsData;
import static mpp.vlad_dani.server.repository.XmlService.loadMoviesData;
import static mpp.vlad_dani.server.repository.XmlService.*;

public class XmlRepo<ID, T extends BaseEntity<ID>> extends MemoryRepository<ID, T> {
    String fileName;
    /**
     * This repository provides CRUD operations and validation for entities.
     *
     * @param validator A validator for the type object.
     */
    public XmlRepo(Validator<T> validator, String fileName) {
        super(validator);
        this.fileName= fileName;
    }

    private void saveAll(List<?> lb) {
        if (lb == null)
            return;
        lb.forEach(e -> {
            try {
                T obj = (T) e;
                save(obj);
            }catch(Exception re) {
                re.printStackTrace();
            }
        });
    }

    public void loadContent(Class<T> cl) throws Exception {
        try {
            if ( cl.getSimpleName().equals("Movie")) {
                List<Movie> lm = loadMoviesData(fileName);
                saveAll(lm);
            }

            if ( cl.getSimpleName().equals("Client")) {
                List<Client> lc = loadClientsData(fileName);
                saveAll(lc);
            }

            if ( cl.getSimpleName().equals("Rental")) {
                List<Rental> lr = loadRentalsData(fileName);
                saveAll(lr);
            }

        }catch(Exception e) {
            throw new Exception("Could not load data");
        }
    }


    @Override
    public Optional<T> save(T entity) throws Exception {
        Optional<T> opt = super.save(entity);
        if (opt.isPresent()) {
            return opt;
        }
        writeXML();
        return Optional.empty();
    }

    @Override
    public Optional<T> update(T entity) throws Exception {
        Optional<T> opt = super.update(entity);
        writeXML();
        return opt;
    }

    @Override
    public Optional<T> delete(ID id)  {
        Optional<T> opt = super.delete(id);
        try{
        writeXML();}
        catch (Exception e){
            e.printStackTrace();
        }
        return opt;
    }

    private void saveData(Document document, T entity) throws Exception {
        if ( entity instanceof Client )
            saveClientData(document, (Client)entity, fileName);
        if ( entity instanceof Movie )
            saveMovieData(document, (Movie)entity, fileName);
        if ( entity instanceof Rental )
            saveRentalData(document, (Rental)entity, fileName);
    }

    public void writeXML() throws Exception {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = document.createElement("moviestore");
            document.appendChild(root);

            for (T entity : findAll()) {
                saveData(document, entity);
            }
        }catch(Exception e) {
            throw new Exception("XML error " + fileName);
        }
    }
}