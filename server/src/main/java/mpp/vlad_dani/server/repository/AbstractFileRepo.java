package mpp.vlad_dani.server.repository;

import mpp.vlad_dani.common.domain.BaseEntity;
import mpp.vlad_dani.common.domain.validator.Validator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class AbstractFileRepo<ID,T extends BaseEntity<ID>> extends MemoryRepository<ID, T> {
    public String filename;

    public AbstractFileRepo(Validator<T> validator, String filename){
        super(validator);
        this.filename=filename;

        this.loadData();
    }

    private void loadData(){
        Path path = Paths.get(this.filename);
        try{
            Files.lines(path).forEach(line->{
                List<String> list= Arrays.asList(line.split(","));
                try{
                    super.save(this.createEntity(list));
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void saveData(){
        try (PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter(this.filename, false)))) {
            this.elements().forEach(el -> file.println(this.toFile(el)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract String toFile(T entity);

    public abstract T createEntity(List<String> list);

    private List<T> elements(){
        return StreamSupport.stream(super.findAll().spliterator(),false).collect(Collectors.toList());
    }

    @Override
    public Optional<T> save(T entity) throws Exception {
        Optional<T> optional = super.save(entity);
        saveData();
        return optional;
    }

    public Optional<T> delete(ID id){
        Optional<T> optional = super.delete(id);
        saveData();
        return optional;
    }

    public Optional<T> update(T entity) throws Exception {
        Optional<T> optional = super.update(entity);
        saveData();
        return optional;
    }
}
