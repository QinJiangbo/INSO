/**  
 * @description manage the jdbc proceedure
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 16, 2015 4:04:01 PM
 */

package Engine;

import Util.DBOperator;
import Database.BtChinaJDBC;
import Database.BtTianTangJDBC;
import Database.DM1080pJDBC;
import Database.DMMJDBC;
import Database.DYTTJDBC;
import Database.DYXZJDBC;
import Database.OurReleaseJDBC;
import Database.ThreeMuJDBC;
import Database.TorrentbarJDBC;
import Database.XiXiHDJDBC;
import Database.XiXiZhanJDBC;
import Database.YSJDBC;
import Database.Yify2JDBC;
import Database.YifyJDBC;
import Database.YifyM2JDBC;
import Database.YifyMJDBC;
import Database.ZerodmJDBC;
import Database.ZiMuKuJDBC;

public class StartJDBC {

	public static void main(String[] args) {
		XiXiZhanJDBC jdbc1 = new XiXiZhanJDBC();
		XiXiHDJDBC jdbc2 = new XiXiHDJDBC();
		YifyJDBC jdbc3 = new YifyJDBC();
		OurReleaseJDBC jdbc4 = new OurReleaseJDBC();
		BtTianTangJDBC jdbc5 = new BtTianTangJDBC();
		YSJDBC jdbc6 = new YSJDBC();
		DYTTJDBC jdbc7 = new DYTTJDBC();
		BtChinaJDBC jdbc8 = new BtChinaJDBC();
		ThreeMuJDBC jdbc9 = new ThreeMuJDBC();
		DYXZJDBC jdbc10 = new DYXZJDBC();
		ZiMuKuJDBC jdbc11 = new ZiMuKuJDBC();
		YifyMJDBC jdbc12 = new YifyMJDBC();
		TorrentbarJDBC jdbc13 = new TorrentbarJDBC();
		DMMJDBC jdbc14 = new DMMJDBC();
		DM1080pJDBC jdbc15 = new DM1080pJDBC();
		ZerodmJDBC jdbc16 = new ZerodmJDBC();
		Yify2JDBC jdbc17 = new Yify2JDBC();
		YifyM2JDBC jdbc18 = new YifyM2JDBC();
		
		Object[] jdbcs = { jdbc1, jdbc2, jdbc3, jdbc4, jdbc5, jdbc6, 
				  		   jdbc7, jdbc8, jdbc9, jdbc10, jdbc11, jdbc12,
				  		   jdbc13, jdbc14, jdbc15, jdbc16, jdbc17, jdbc18};

		for(Object o : jdbcs) {
			DBOperator jdbc = (DBOperator) o;
			jdbc.insertDB();
		}
	}

}
