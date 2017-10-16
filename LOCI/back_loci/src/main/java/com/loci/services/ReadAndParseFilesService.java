package com.loci.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.loci.object.Station;
import com.loci.object.Subway;

@Service
public class ReadAndParseFilesService{
	
	public final static String  tab[] = {"1","2","4","6","9"};
	

	private Station station;
	
	ArrayList<String> nameStations = new ArrayList<String>();
	Map<String,List<Long>> map = new HashMap<String, List<Long>>();
	ArrayList<Station> stations = new ArrayList<Station>();
	List<Map<Long, Long>> allIdToId = new ArrayList<Map<Long,Long>>();
	
	public List<String> read(String filePath) throws IOException {
		List<String> lines = new ArrayList<String>();
		try{	
	        BufferedReader buf = new BufferedReader(new FileReader(filePath));	
	        String line;
			while ((line=buf.readLine()) != null) {
				lines.add(line);
			}
			buf.close();
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
		return lines;
	}
	
	public ArrayList<Station> parseStopsFile(List<String> lines) {
		try{
			lines.remove(0);
			String newLine;
			for (String line : lines) {
				station = new Station();
				newLine = line.replace(", ","-");
				String[] paramOfStations = newLine.split(",");
				List<String> parametersOfStation = new ArrayList<String>();
				for (String param : paramOfStations) {
					parametersOfStation.add(param);	
				}
				
				station.setName(parametersOfStation.get(2).replace("\"", ""));
				station.setDescription(parametersOfStation.get(3).replace("\"", ""));
				station.setLatitude(Double.parseDouble(parametersOfStation.get(4)));
				station.setLongitude(Double.parseDouble(parametersOfStation.get(5)));
				

				if(map.containsKey(parametersOfStation.get(2).replace("\"", ""))) {
					map.get(parametersOfStation.get(2).replace("\"", "")).add(Long.parseLong(parametersOfStation.get(0)));			
				} else {
					List<Long> idList = new ArrayList<Long>();
					idList.add(Long.parseLong(parametersOfStation.get(0)));
					map.put(parametersOfStation.get(2).replace("\"", ""),idList);
				}

				if(!nameStations.contains(parametersOfStation.get(2).replace("\"", ""))) {
					nameStations.add(parametersOfStation.get(2).replace("\"", ""));	
					station.setIds(map.get(parametersOfStation.get(2).replace("\"", "")));
					stations.add(station);
				}
		    }
			
		} catch(Exception e) {
            System.err.println(e.getCause());
        }
		return stations;
	}

	
	/**
	 * Parse the file stop_times.txt.
	 * @param lines
	 * @param sub
	 * @param stopBySubwayCount
	 */

	public void parseStopTimesFile(List<String> lines, String strSubway, Subway sub) {
		List<String> test = new ArrayList<String>();
		for(int i =0; i<tab.length;i++) {
			test.add(tab[i]);
		}
		
		if(test.contains(strSubway)) {
			List<String> newLines = lines.subList(1, sub.getNumberOfStops()+1);
			creationOfEdges(newLines, true);
		} else {
			lines.remove(0);
			creationOfEdges(lines, false);
		}
	}
	
	/**
	 * creationOfEdges()
	 * @param lines
	 * @param hasToCreateTwoEdges
	 */
	public void creationOfEdges(List<String> lines, boolean hasToCreateTwoEdges) {
		int previousPos = 0;
		Long previousIdStop = null;
		for (String  line : lines) {
			Map<Long, Long> idToId = new HashMap<Long, Long>();
			List<String> parameters = new ArrayList<String>();
			String[] param = line.split(",");
			for(String str : param) {
				parameters.add(str);
			}
			int pos = Integer.parseInt(parameters.get(4));
			Long idStop = Long.parseLong(parameters.get(3));
			idToId.put(previousIdStop, idStop);
			
			if(pos == previousPos+1 && previousIdStop != null) {
				if(!allIdToId.contains(idToId)) {
					if(hasToCreateTwoEdges == true) {
						NetworkService.createEdges(previousIdStop, idStop);
						NetworkService.createEdges(idStop, previousIdStop);
					} else {
						NetworkService.createEdges(previousIdStop, idStop);
					}
					
					allIdToId.add(idToId);
				}	
			}
			previousPos = pos;
			previousIdStop = idStop;
		}
	}
}

