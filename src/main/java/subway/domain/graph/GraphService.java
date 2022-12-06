package subway.domain.graph;

import subway.domain.station.Station;
import subway.domain.station.StationRepository;
import subway.domain.util.MessageFactory;

import static subway.domain.util.ErrorCode.*;
import static subway.domain.util.SetupConstant.*;

public class GraphService {
    private final MessageFactory messageFactory = new MessageFactory();

    private static final Graph distanceGraph = new Graph();
    private static final Graph timeGraph = new Graph();

    void setUp() {
        setUpDistanceGraph();
        setUpTimeGraph();
    }


    String findShortestDistance(String departStationName, String arriveStationName) {
        Station departStation = findPresentStation(departStationName);
        Station arriveStation = findPresentStation(arriveStationName);
        validateStationNames(departStation, arriveStation);
        return "";
    }

    private void setUpDistanceGraph() {
        updateDistanceGraph(STATION_GYODAE, STATION_GANGNAM, DIST_GYODAE_TO_GANGNAM);
        updateDistanceGraph(STATION_GANGNAM, STATION_YEOKSAM, DIST_GANGNAM_TO_YEOKSAM);
        updateDistanceGraph(STATION_GYODAE, STATION_NAMBU_TERMINAL, DIST_GYODAE_TO_NAMBU_TERMINAL);
        updateDistanceGraph(STATION_NAMBU_TERMINAL, STATION_YANGJAE, DIST_NAMBU_TERMINAL_TO_YANGJAE);
        updateDistanceGraph(STATION_YANGJAE, STATION_MAEBONG, DIST_YANGJAE_TO_MAEBONG);
        updateDistanceGraph(STATION_GANGNAM, STATION_YANGJAE, DIST_GANGNAM_TO_YANGJAE);
        updateDistanceGraph(STATION_YANGJAE, STATION_YANGJAE_CITIZENS_FOREST, DIST_YANGJAE_TO_YANGJAE_CITIZEN_FOREST);
    }

    private void setUpTimeGraph() {
        updateTimeGraph(STATION_GYODAE, STATION_GANGNAM, TIME_GYODAE_TO_GANGNAM);
        updateTimeGraph(STATION_GANGNAM, STATION_YEOKSAM, TIME_GANGNAM_TO_YEOKSAM);
        updateTimeGraph(STATION_GYODAE, STATION_NAMBU_TERMINAL, TIME_GYODAE_TO_NAMBU_TERMINAL);
        updateTimeGraph(STATION_NAMBU_TERMINAL, STATION_YANGJAE, TIME_NAMBU_TERMINAL_TO_YANGJAE);
        updateTimeGraph(STATION_YANGJAE, STATION_MAEBONG, TIME_YANGJAE_TO_MAEBONG);
        updateTimeGraph(STATION_GANGNAM, STATION_YANGJAE, TIME_GANGNAM_TO_YANGJAE);
        updateTimeGraph(STATION_YANGJAE, STATION_YANGJAE_CITIZENS_FOREST, TIME_YANGJAE_TO_YANGJAE_CITIZEN_FOREST);
    }

    private void updateDistanceGraph(String stationName1, String stationName2, int pathWeight) {
        Station station1 = StationRepository.findByName(stationName1);
        Station station2 = StationRepository.findByName(stationName2);
        distanceGraph.updateGraph(station1, station2, pathWeight);
    }

    private void updateTimeGraph(String stationName1, String stationName2, int pathWeight) {
        Station station1 = StationRepository.findByName(stationName1);
        Station station2 = StationRepository.findByName(stationName2);
        timeGraph.updateGraph(station1, station2, pathWeight);
    }

    private void validateStationNames(Station departStation, Station arriveStation) {
        if (departStation == arriveStation) {
            throw new IllegalArgumentException(messageFactory.makeErrorMessage(DUPLICATE_STATION_NAME));
        }
    }

    private Station findPresentStation(String stationName) {
        Station station = StationRepository.findByName(stationName);
        if (station == null) {
            throw new IllegalArgumentException(messageFactory.makeErrorMessage(STATION_NOT_FOUND));
        }
        return station;
    }

}
