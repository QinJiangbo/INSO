/**  
 * @description indexer factory for creating indexers
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date May 23, 2015 5:32:00 PM
 */

package Index;

public class IndexerFactory {
	
	/**
	 * get the indexer objects of the FilmIndexer class
	 * @param name indexer name
	 * @return the indexer object
	 */
	public static FilmIndexer getFilmIndxer(String name){
		FilmIndexer indexer = new FilmIndexer(name);
		return indexer;
	}
	
	/**
	 * get bashes objects of the FilmIndexer objects
	 * @param names the name set of the indexer names
	 * @return the set of the objects
	 */
	public static FilmIndexer[] getBashIndexers(String[] names){
		int length = names.length;
		FilmIndexer[] indexers = new FilmIndexer[length];
		for(int i = 0; i < length; i++) {
			FilmIndexer indexer = new FilmIndexer(names[i]);
			indexers[i] = indexer;
		}
		return indexers;
	}
}
