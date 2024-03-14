package Tasks;


import java.sql.SQLException;
import java.util.List;

public class ClientServiceCaller {
    public static void main(String[] args) {
        ClientService clientService = new ClientService();

        try {
            // Створення нового клієнта
            long newClientId = clientService.create("John Doe");
            System.out.println("New client ID: " + newClientId);

            // Отримання імені клієнта за його ідентифікатором
            long clientIdToRetrieve = 1;
            String clientName = clientService.getById(clientIdToRetrieve);
            System.out.println("Client name with ID " + clientIdToRetrieve + ": " + clientName);

            // Встановлення нового імені для клієнта
            long clientIdToUpdate = 1;
            String newClientName = "Jane Doe";
            clientService.setName(clientIdToUpdate, newClientName);
            System.out.println("Client name updated successfully.");

            // Видалення клієнта за його ідентифікатором
            long clientIdToDelete = 2;
            clientService.deleteById(clientIdToDelete);
            System.out.println("Client with ID " + clientIdToDelete + " deleted successfully.");

            // Отримання списку всіх клієнтів
            List<Client> allClients = clientService.listAll();
            System.out.println("All clients:");
            for (Client client : allClients) {
                System.out.println("ID: " + client.getId() + ", Name: " + client.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}