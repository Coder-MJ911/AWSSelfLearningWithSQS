package aurora;

import java.sql.SQLException;

class AuroraMain {
    public static void main(String []args) throws SQLException, ClassNotFoundException {
        AuroraTest aurora = new AuroraTest("newpostgres.cluster-cloegco0jlj8.ap-southeast-2.rds.amazonaws.com","postgres","qazwsxedc123");

        aurora.createDB();
        aurora.insertData();
        aurora.selectData();
    }
}