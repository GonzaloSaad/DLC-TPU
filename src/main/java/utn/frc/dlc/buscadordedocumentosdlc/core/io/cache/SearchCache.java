package utn.frc.dlc.buscadordedocumentosdlc.core.io.cache;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utn.frc.dlc.buscadordedocumentosdlc.core.io.management.PostPackManagement;
import utn.frc.dlc.buscadordedocumentosdlc.core.model.PostList;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;


public class SearchCache extends Cache {

    private static final Logger logger = Logger.getLogger(SearchCache.class.getName());
    private int pointer;
    private Map<Integer, Integer> cacheMap;

    public SearchCache(int size) {
        super(size);
        cacheMap = new HashMap<>();
        pointer = 0;
    }

    @Override
    public Map<String, PostList> getPostPack(int file) {
        CachedPostPack cachedPostPack;
        CachedPostPack storedPostPack;

        Integer indexOfCache = cacheMap.get(file);

        if (indexOfCache != null) {
            cachedPostPack = get(indexOfCache);
            cachedPostPack.markUsed();
            return cachedPostPack.getPostPack();
        }


        int indexOfLessUsedCache = getLessUsedPostPackIndex();


        cachedPostPack = get(indexOfLessUsedCache);
        if (cachedPostPack !=null) {
            cacheMap.put(cachedPostPack.getFile(), null);
        }

        storedPostPack = getPostPackFromStorage(file);
        set(storedPostPack, indexOfLessUsedCache);
        cacheMap.put(storedPostPack.getFile(), indexOfLessUsedCache);
        logger.log(Level.INFO, MessageFormat.format("Stored recovered [{0}]. Cache occupancy [{1}]",storedPostPack.getFile(), occupancy()));



        return storedPostPack.getPostPack();
    }

    private CachedPostPack getPostPackFromStorage(int file) {
        Map<String, PostList> postPack = PostPackManagement.getInstance().getPostPack(file);
        if (postPack == null) {
            throw new IllegalStateException("The file was not found! Inconsistency in the model!");
        }

        return new CachedPostPack(file, postPack);
    }

    public int getLessUsedPostPackIndex() {

        int index = pointer;

        do {
            CachedPostPack cpp = get(index);

            if (cpp == null || !cpp.used()) {
                pointer = index;
                return index;
            } else {
                cpp.markNotUsed();
            }
            index = (index + 1) % size();

        } while (true);
    }

    @Override
    public Map<String, PostList> putPostPack(Map<String, PostList> postPack, int file) {
        throw new UnsupportedOperationException("Search Cache cant add files.");
    }

    @Override
    public void dump(boolean parallel) {
        clean();
    }

    @Override
    public void update() {
        for (CachedPostPack c: getCache()){
            if (c!=null){
                CachedPostPack dp = getPostPackFromStorage(c.getFile());
                Integer index = cacheMap.get(c.getFile());
                if (index!=null){
                    set(dp,index);
                }
            }
        }
    }

    private double occupancy() {
        int cached = 0;
        for (CachedPostPack c : getCache()) {
            if (c != null) {
                cached++;
            }
        }
        return (double) cached / (double) size();
    }
}


