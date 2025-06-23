package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import db.DB;
import db.DbExeption;
import DAO.ElectronicsDAO;
import DAO.FurnitureDAO;
import DAO.HortifrutiDAO;
import entities.Electronics;
import entities.Furniture;
import entities.Hortifruti;

public class Main {

    public static void main(String[] args) {

        //Requisitos p/teste

        Connection conn = null;
        Statement st = null;        int opt = 0, opt2 = 0;
        // ResultSet rs = null;
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); //Padrao de formatação 
        Scanner scan = new Scanner(System.in);
        ElectronicsDAO electronicsDAO = new ElectronicsDAO();
        FurnitureDAO furnitureDAO = new FurnitureDAO();
        HortifrutiDAO hortifrutiDAO = new HortifrutiDAO();
        //Testes de banco

        try {
            conn = DB.getConnection();
            conn.setAutoCommit(false);
            do {
                System.out.println("====== SISTEMA DE GERENCIAMENTO DE ESTOQUE ======\n");
                System.out.println("1- Eletronicos");
                System.out.println("2- Moveis");
                System.out.println("3- Hortifruit");
                System.out.println("0- Sair\n");

                System.out.printf("Selecione uma opção: ");
                opt = scan.nextInt(); scan.nextLine();

                switch (opt) {
                    case 1:
                        System.out.println("====== GERENCIAMENTO DE Eletronicos ======\n");
                        System.out.println("1- Inserir novo eletronico");
                        System.out.println("2- Deletar eletronico");
                        System.out.println("3- Buscar eletronico por id");
                        System.out.println("4- Listar eletronicos existentes");
                        System.out.println("5- Alterar eletronico");
                        System.out.println("0- Voltar");

                        System.out.printf("Escolha uma opção: ");
                        opt2 = scan.nextInt(); scan.nextLine();
                        switch (opt2) {
                            case 1:
                                try {
                                    System.out.println("=== Inserir Eletrônico ===");
                                    System.out.print("Nome: ");
                                    String name = scan.nextLine();
                                    System.out.print("Preço: ");
                                    double price = scan.nextDouble();
                                    System.out.print("Quantidade: ");
                                    int quantity = scan.nextInt();
                                    scan.nextLine(); 
                                    System.out.print("Marca: ");
                                    String mark = scan.nextLine();
                                    System.out.print("Fabricante: ");
                                    String fabricator = scan.nextLine();
                                    System.out.print("Modelo: ");
                                    String model = scan.nextLine();
                                    System.out.print("Data de lançamento (dd/MM/yyyy): ");
                                    String dateStr = scan.nextLine();
                                    Date yearLaunch = sdf.parse(dateStr);

                                    Electronics electronic = new Electronics(0, name, price, quantity, mark, fabricator, model, yearLaunch);
                                    electronicsDAO.insert(electronic);
                                    System.out.println("Eletrônico inserido com sucesso!");
                                } catch (Exception e) {
                                    System.err.println("Erro ao inserir eletrônico: " + e.getMessage());
                                }
                                break;

                            case 2:
                                try {
                                    System.out.print("Digite o ID do eletrônico a ser deletado: ");
                                    int deleteId = scan.nextInt();
                                    Electronics existing = electronicsDAO.findById(deleteId);
                                    if (existing != null) {
                                        System.out.println("Eletrônico a ser deletado:");
                                        existing.exhibitionProductsData();
                                        System.out.print("Confirma a exclusão? (s/n): ");
                                        String confirm = scan.next();
                                        if (confirm.equalsIgnoreCase("s")) {
                                            electronicsDAO.deleteById(deleteId);
                                            System.out.println("Eletrônico deletado com sucesso!");
                                        } else {
                                            System.out.println("Exclusão cancelada.");
                                        }
                                    } else {
                                        System.out.println("Eletrônico não encontrado.");
                                    }
                                } catch (Exception e) {
                                    System.err.println(" Erro ao deletar eletrônico: " + e.getMessage());
                                }
                                break;

                            case 3:
                                try {
                                    System.out.print("Digite o ID do eletrônico: ");
                                    int searchId = scan.nextInt();
                                    Electronics electronic = electronicsDAO.findById(searchId);
                                    if (electronic != null) {
                                        System.out.println("=== Eletrônico Encontrado ===");
                                        electronic.exhibitionProductsData();
                                    } else {
                                        System.out.println("Eletrônico não encontrado.");
                                    }
                                } catch (Exception e) {
                                    System.err.println(" Erro ao buscar eletrônico: " + e.getMessage());
                                }
                                break;

                            case 4:
                                try {
                                    System.out.println("=== Lista de Eletrônicos ===");
                                    List<Electronics> electronics = electronicsDAO.findAllElectronics();
                                    if (electronics.isEmpty()) {
                                        System.out.println("Nenhum eletrônico encontrado.");
                                    } else {
                                        for (Electronics e : electronics) {
                                            e.exhibitionProductsData();
                                            System.out.println("--------------------");
                                        }
                                    }
                                } catch (Exception e) {
                                    System.err.println(" Erro ao listar eletrônicos: " + e.getMessage());
                                }
                                break;

                            case 5:
                                try {
                                    System.out.print("Digite o ID do eletrônico a ser atualizado: ");
                                    int updateId = scan.nextInt();
                                    scan.nextLine();
                                    
                                    Electronics existing = electronicsDAO.findById(updateId);
                                    if (existing == null) {
                                        System.out.println("Eletrônico não encontrado.");
                                        break;
                                    }

                                    System.out.println("=== Dados atuais ===");
                                    existing.exhibitionProductsData();
                                    
                                    System.out.println("\n=== Novos Dados ===");
                                    System.out.print("Nome: ");
                                    String newName = scan.nextLine();
                                    System.out.print("Preço: ");
                                    double newPrice = scan.nextDouble();
                                    System.out.print("Quantidade: ");
                                    int newQuantity = scan.nextInt();
                                    scan.nextLine();
                                    System.out.print("Marca: ");
                                    String newMark = scan.nextLine();
                                    System.out.print("Fabricante: ");
                                    String newFabricator = scan.nextLine();
                                    System.out.print("Modelo: ");
                                    String newModel = scan.nextLine();
                                    System.out.print("Data de lançamento (dd/MM/yyyy): ");
                                    String newDateStr = scan.nextLine();
                                    Date newYearLaunch = sdf.parse(newDateStr);

                                    Electronics updated = new Electronics(updateId, newName, newPrice, newQuantity, newMark, newFabricator, newModel, newYearLaunch);
                                    electronicsDAO.update(updateId, updated);
                                    System.out.println("Eletrônico atualizado com sucesso!");
                                } catch (Exception e) {
                                    System.err.println("Erro ao atualizar eletrônico: " + e.getMessage());
                                }
                                break;

                            case 0:
                                System.out.println("Voltando ao menu principal...");
                                break;
                        
                            default:
                                System.out.println("Opção inválida!");
                                break;
                        }
                        conn.commit();
                        break; 
                    case 2:
                        System.out.println("====== GERENCIAMENTO DE Móveis ======\n");
                        System.out.println("1- Inserir novo móvel");
                        System.out.println("2- Deletar móvel");
                        System.out.println("3- Buscar móvel por id");
                        System.out.println("4- Listar móveis existentes");
                        System.out.println("5- Alterar móvel");
                        System.out.println("0- Voltar");

                        System.out.printf("Escolha uma opção: ");
                        opt2 = scan.nextInt(); scan.nextLine();

                        switch (opt2) {
                            case 1:
                                try {
                                    System.out.println("=== Inserir Móvel ===");
                                    System.out.print("Nome: ");
                                    String name = scan.nextLine();
                                    System.out.print("Preço: ");
                                    double price = scan.nextDouble();
                                    System.out.print("Quantidade: ");
                                    int quantity = scan.nextInt();
                                    System.out.print("Altura (m): ");
                                    double height = scan.nextDouble();
                                    System.out.print("Largura (m): ");
                                    double width = scan.nextDouble();
                                    scan.nextLine();
                                    System.out.print("Material: ");
                                    String material = scan.nextLine();

                                    Furniture furniture = new Furniture(0, name, price, quantity, height, width, material);
                                    furnitureDAO.insert(furniture);
                                    System.out.println("Móvel inserido com sucesso!");
                                } catch (Exception e) {
                                    System.err.println(" Erro ao inserir móvel: " + e.getMessage());
                                }
                                break;

                            case 2:
                                try {
                                    System.out.print("Digite o ID do móvel a ser deletado: ");
                                    int deleteId = scan.nextInt();
                                    Furniture existing = furnitureDAO.findById(deleteId);
                                    if (existing != null) {
                                        System.out.println("Móvel a ser deletado:");
                                        existing.exhibitionProductsData();
                                        System.out.print("Confirma a exclusão? (s/n): ");
                                        String confirm = scan.next();
                                        if (confirm.equalsIgnoreCase("s")) {
                                            furnitureDAO.deleteById(deleteId);
                                            System.out.println("Móvel deletado com sucesso!");
                                        } else {
                                            System.out.println("Exclusão cancelada.");
                                        }
                                    } else {
                                        System.out.println("Móvel não encontrado.");
                                    }
                                } catch (Exception e) {
                                    System.err.println(" Erro ao deletar móvel: " + e.getMessage());
                                }
                                break;

                            case 3:
                                try {
                                    System.out.print("Digite o ID do móvel: ");
                                    int searchId = scan.nextInt();
                                    Furniture furniture = furnitureDAO.findById(searchId);
                                    if (furniture != null) {
                                        System.out.println("=== Móvel Encontrado ===");
                                        furniture.exhibitionProductsData();
                                    } else {
                                        System.out.println("Móvel não encontrado.");
                                    }
                                } catch (Exception e) {
                                    System.err.println(" Erro ao buscar móvel: " + e.getMessage());
                                }
                                break;

                            case 4:
                                try {
                                    System.out.println("=== Lista de Móveis ===");
                                    List<Furniture> furnitures = furnitureDAO.findAllFurniture();
                                    if (furnitures.isEmpty()) {
                                        System.out.println("Nenhum móvel encontrado.");
                                    } else {
                                        for (Furniture f : furnitures) {
                                            f.exhibitionProductsData();
                                            System.out.println("--------------------");
                                        }
                                    }
                                } catch (Exception e) {
                                    System.err.println(" Erro ao listar móveis: " + e.getMessage());
                                }
                                break;

                            case 5:
                                try {
                                    System.out.print("Digite o ID do móvel a ser atualizado: ");
                                    int updateId = scan.nextInt();
                                    scan.nextLine();
                                    
                                    Furniture existing = furnitureDAO.findById(updateId);
                                    if (existing == null) {
                                        System.out.println("Móvel não encontrado.");
                                        break;
                                    }

                                    System.out.println("=== Dados atuais ===");
                                    existing.exhibitionProductsData();
                                    
                                    System.out.println("\n=== Novos Dados ===");
                                    System.out.print("Nome: ");
                                    String newName = scan.nextLine();
                                    System.out.print("Preço: ");
                                    double newPrice = scan.nextDouble();
                                    System.out.print("Quantidade: ");
                                    int newQuantity = scan.nextInt();
                                    System.out.print("Altura (m): ");
                                    double newHeight = scan.nextDouble();
                                    System.out.print("Largura (m): ");
                                    double newWidth = scan.nextDouble();
                                    scan.nextLine();
                                    System.out.print("Material: ");
                                    String newMaterial = scan.nextLine();

                                    Furniture updated = new Furniture(updateId, newName, newPrice, newQuantity, newHeight, newWidth, newMaterial);
                                    furnitureDAO.update(updateId, updated);
                                    System.out.println("Móvel atualizado com sucesso!");
                                } catch (Exception e) {
                                    System.err.println(" Erro ao atualizar móvel: " + e.getMessage());
                                }
                                break;

                            case 0:
                                System.out.println("Voltando ao menu principal...");
                                break;
                        
                            default:
                                System.out.println("Opção inválida!");
                                break;
                        }
                        conn.commit();
                        break;

                    case 3:
                        System.out.println("====== GERENCIAMENTO DE Hortifruti ======\n");
                        System.out.println("1- Inserir novo hortifruti");
                        System.out.println("2- Deletar hortifruti");
                        System.out.println("3- Buscar hortifruti por id");
                        System.out.println("4- Listar hortifrutis existentes");
                        System.out.println("5- Alterar hortifruti");
                        System.out.println("0- Voltar");

                        System.out.printf("Escolha uma opção: ");
                        opt2 = scan.nextInt(); scan.nextLine();

                        switch (opt2) {
                            case 1:
                                try {
                                    System.out.println("=== Inserir Hortifruti ===");
                                    System.out.print("Nome: ");
                                    String name = scan.nextLine();
                                    System.out.print("Preço por kg: ");
                                    double price = scan.nextDouble();
                                    System.out.print("Quantidade (unidades): ");
                                    int quantity = scan.nextInt();
                                    System.out.print("Peso médio por unidade (g): ");
                                    double middleWeight = scan.nextDouble();

                                    Hortifruti hortifruti = new Hortifruti(0, name, price, quantity, middleWeight);
                                    hortifrutiDAO.insert(hortifruti);
                                    System.out.println("Hortifruti inserido com sucesso!");
                                } catch (Exception e) {
                                    System.err.println(" Erro ao inserir hortifruti: " + e.getMessage());
                                }
                                break;

                            case 2:
                                try {
                                    System.out.print("Digite o ID do hortifruti a ser deletado: ");
                                    int deleteId = scan.nextInt();
                                    Hortifruti existing = hortifrutiDAO.findById(deleteId);
                                    if (existing != null) {
                                        System.out.println("Hortifruti a ser deletado:");
                                        existing.exhibitionHortifruti();
                                        System.out.print("Confirma a exclusão? (s/n): ");
                                        String confirm = scan.next();
                                        if (confirm.equalsIgnoreCase("s")) {
                                            hortifrutiDAO.deleteById(deleteId);
                                            System.out.println("Hortifruti deletado com sucesso!");
                                        } else {
                                            System.out.println("Exclusão cancelada.");
                                        }
                                    } else {
                                        System.out.println("Hortifruti não encontrado.");
                                    }
                                } catch (Exception e) {
                                    System.err.println(" Erro ao deletar hortifruti: " + e.getMessage());
                                }
                                break;

                            case 3:
                                try {
                                    System.out.print("Digite o ID do hortifruti: ");
                                    int searchId = scan.nextInt();
                                    Hortifruti hortifruti = hortifrutiDAO.findById(searchId);
                                    if (hortifruti != null) {
                                        System.out.println("=== Hortifruti Encontrado ===");
                                        hortifruti.exhibitionHortifruti();
                                    } else {
                                        System.out.println("Hortifruti não encontrado.");
                                    }
                                } catch (Exception e) {
                                    System.err.println(" Erro ao buscar hortifruti: " + e.getMessage());
                                }
                                break;

                            case 4:
                                try {
                                    System.out.println("=== Lista de Hortifruti ===");
                                    List<Hortifruti> hortifrutis = hortifrutiDAO.findAllHortifruti();
                                    if (hortifrutis.isEmpty()) {
                                        System.out.println("Nenhum hortifruti encontrado.");
                                    } else {
                                        for (Hortifruti h : hortifrutis) {
                                            h.exhibitionHortifruti();
                                            System.out.println("--------------------");
                                        }
                                    }
                                } catch (Exception e) {
                                    System.err.println(" Erro ao listar hortifruti: " + e.getMessage());
                                }
                                break;

                            case 5:
                                try {
                                    System.out.print("Digite o ID do hortifruti a ser atualizado: ");
                                    int updateId = scan.nextInt();
                                    scan.nextLine();
                                    
                                    Hortifruti existing = hortifrutiDAO.findById(updateId);
                                    if (existing == null) {
                                        System.out.println("Hortifruti não encontrado.");
                                        break;
                                    }

                                    System.out.println("=== Dados atuais ===");
                                    existing.exhibitionHortifruti();
                                    
                                    System.out.println("\n=== Novos Dados ===");
                                    System.out.print("Nome: ");
                                    String newName = scan.nextLine();
                                    System.out.print("Preço por kg: ");
                                    double newPrice = scan.nextDouble();
                                    System.out.print("Quantidade (unidades): ");
                                    int newQuantity = scan.nextInt();
                                    System.out.print("Peso médio por unidade (g): ");
                                    double newMiddleWeight = scan.nextDouble();

                                    Hortifruti updated = new Hortifruti(updateId, newName, newPrice, newQuantity, newMiddleWeight);
                                    hortifrutiDAO.update(updateId, updated);
                                    System.out.println("Hortifruti atualizado com sucesso!");
                                } catch (Exception e) {
                                    System.err.println(" Erro ao atualizar hortifruti: " + e.getMessage());
                                }
                                break;

                            case 0:
                                System.out.println("Voltando ao menu principal...");
                                break;
                        
                            default:
                                System.out.println("Opção inválida!");
                                break;
                        }
                        conn.commit();
                        break;

                    case 0:
                        System.out.println("Finalizando...");
                        break;
                
                    default:
                        break;
                }

            } while (opt != 0);
            conn.commit();
            
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
                throw new DbExeption("Falha na transação rollback concluido: " + e.getMessage());
            } catch (SQLException e1) {
                throw new DbExeption("Erro no rollback: " + e1.getMessage());
            }
        } finally {
            if (scan != null) {
                scan.close();
            }
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}
          

