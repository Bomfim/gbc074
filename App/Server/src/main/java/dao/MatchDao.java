// package dao;

// import entity.Match;

// import java.io.FileInputStream;
// import java.io.FileOutputStream;
// import java.io.ObjectInputStream;
// import java.io.ObjectOutputStream;
// import java.util.ArrayList;
// import java.util.List;

// public class MatchDao {

//     private static final String fileUrl = "/Users/brunofrcosta/Desktop/matches.dat";

//     public void persist(Match match) {
//         List<Match> matches = readAll();
//         Integer newId = 1;
//         if (matches == null) {
//             matches = new ArrayList<Match>();
//         } else {
//             newId = matches.get(matches.size() - 1).getId() + 1;
//         }
//         match.setId(newId);
//         matches.add(match);
//         save(matches);
//     }

//     public void edit(Match match) {
//         List<Match> matches = readAll();
//         for (Match oldMatch : matches) {
//             if (oldMatch.getId().equals(match.getId())) {
//                 matches.remove(oldMatch);
//                 matches.add(match);
//                 break;
//             }
//         }
//         save(matches);
//     }

//     public void delete(Match match) {
//         List<Match> matches = readAll();
//         matches.remove(findById(match.getId()));
//         save(matches);
//     }

//     public Match findById(Integer id) {
//         List<Match> matches = readAll();
//         for (Match match : matches) {
//             if (match.getId().equals(id)) {
//                 return match;
//             }
//         }
//         return null;
//     }

//     public List<Match> findAllByReporter(String reporter) {
//         List<Match> matches = readAll();
//         List<Match> result = new ArrayList<>();
//         for (Match match : matches) {
//             if (match.getReporter().equals(reporter)) {
//                 result.add(match);
//             }
//         }
//         return result;
//     }

//     private void save(List<Match> matches) {
//         try {
//             FileOutputStream fileOutputStream = new FileOutputStream(fileUrl);

//             ObjectOutputStream objGravar = new ObjectOutputStream(fileOutputStream);

//             objGravar.writeObject(matches);
//             objGravar.flush();
//             objGravar.close();
//             fileOutputStream.flush();
//             fileOutputStream.close();

//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     public List<Match> readAll() {
//         List<Match> matches = new ArrayList<Match>();

//         try {

//             FileInputStream fileInputStream = new FileInputStream(fileUrl);

//             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//             matches = (ArrayList<Match>) objectInputStream.readObject();

//             objectInputStream.close();
//             fileInputStream.close();

//         } catch (Exception e) {
//             return null;
//         }
//         return matches;
//     }
// }
