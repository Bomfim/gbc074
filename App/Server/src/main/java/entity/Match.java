// package entity;

// import java.io.Serializable;
// import java.util.List;

// public class Match implements Serializable {

//     private Integer id;

//     private String hostTeam;

//     private int hostTeamScore;

//     private String guestTeam;

//     private int guestTeamScore;

//     private boolean inProgress;

//     private String reporter;

//     private List<String> comments;

//     {
//         this.hostTeamScore = 0;
//         this.guestTeamScore = 0;
//     }

//     public Match() {
//     }

//     public Match(String hostTeam, String guestTeam) {
//         this.hostTeam = hostTeam;
//         this.guestTeam = guestTeam;
//     }

//     public Integer getId() {
//         return id;
//     }

//     public void setId(Integer id) {
//         this.id = id;
//     }

//     public String getHostTeam() {
//         return hostTeam;
//     }

//     public void setHostTeam(String hostTeam) {
//         this.hostTeam = hostTeam;
//     }

//     public int getHostTeamScore() {
//         return hostTeamScore;
//     }

//     public void setHostTeamScore(int hostTeamScore) {
//         this.hostTeamScore = hostTeamScore;
//     }

//     public String getGuestTeam() {
//         return guestTeam;
//     }

//     public void setGuestTeam(String guestTeam) {
//         this.guestTeam = guestTeam;
//     }

//     public int getGuestTeamScore() {
//         return guestTeamScore;
//     }

//     public void setGuestTeamScore(int guestTeamScore) {
//         this.guestTeamScore = guestTeamScore;
//     }

//     public boolean isInProgress() {
//         return inProgress;
//     }

//     public void setInProgress(boolean inProgress) {
//         this.inProgress = inProgress;
//     }

//     public String getReporter() {
//         return reporter;
//     }

//     public void setReporter(String reporter) {
//         this.reporter = reporter;
//     }

//     public List<String> getComments() {
//         return comments;
//     }

//     public void setComments(List<String> comments) {
//         this.comments = comments;
//     }

//     @Override
//     public String toString() {
//         return "Match{" + "id=" + id + ", hostTeam='" + hostTeam + '\'' + ", hostTeamScore=" + hostTeamScore
//                 + ", guestTeam='" + guestTeam + '\'' + ", guestTeamScore=" + guestTeamScore + ", inProgress="
//                 + inProgress + ", reporter=" + reporter + ", comments=" + comments + '}';
//     }
// }
