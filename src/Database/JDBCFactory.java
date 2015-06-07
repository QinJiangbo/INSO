/**  
 * @description factory class for creating the jdbc objects
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date May 23, 2015 4:32:47 PM
 */

package Database;

public class JDBCFactory {
	
	/**
	 * get the jdbc objects of the FilmJDBC class
	 * @param name jdbc name
	 * @return the jdbc object
	 */
	public static FilmJDBC getFilmJDBC(String name){
		FilmJDBC jdbc = new FilmJDBC(name);
		return jdbc;
	}
	
	/**
	 * get bashes objects of the FilmJDBC objects
	 * @param names the name set of the jdbc names
	 * @return the set of the objects
	 */
	public static FilmJDBC[] getBashJDBCs(String[] names){
		int length = names.length;
		FilmJDBC[] jdbcs = new FilmJDBC[length];
		for(int i = 0; i < length; i++) {
			FilmJDBC jdbc = new FilmJDBC(names[i]);
			jdbcs[i] = jdbc;
		}
		return jdbcs;
	}
}
