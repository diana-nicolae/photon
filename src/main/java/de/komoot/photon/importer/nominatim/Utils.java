package de.komoot.photon.importer.nominatim;

import com.vividsolutions.jts.geom.Geometry;
import lombok.extern.log4j.Log4j;
import org.elasticsearch.common.collect.Maps;
import org.openstreetmap.osmosis.hstore.PGHStore;
import org.postgis.jts.JtsGeometry;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * date: 16.05.14
 *
 * @author christoph
 */
@Log4j
public class Utils {
	public static Map<String, String> getMap(ResultSet rs, String columnName) throws SQLException {
		Map<String, String> tags = Maps.newHashMap();

		PGHStore dbTags = (PGHStore) rs.getObject(columnName);
		if(dbTags != null) {
			for(Map.Entry<String, String> tagEntry : dbTags.entrySet()) {
				tags.put(tagEntry.getKey(), tagEntry.getValue());
			}
		}

		return tags;
	}

	@Nullable
	public static <T extends Geometry> T extractGeometry(ResultSet rs, String columnName) throws SQLException {
		JtsGeometry geom = (JtsGeometry) rs.getObject(columnName);
		if(geom == null) {
			log.info("no geometry found in column " + columnName);
			return null;
		}
		return (T) geom.getGeometry();
	}
}
