package com.springCoupon.utilities;

public class LoginManager {


    private static LoginManager instance = new LoginManager();


    public static LoginManager getInstance() {
        return instance;
    }

    public MainFacade login(ClientType clientType, String email, String password) {

        switch (clientType) {

            case ADMINISTRATOR:
                if (new AdminFacade(new CompaniesDBDAO(), new CouponsDBDAO(), new CustomersDBDAO()).login(email, password)) {
                    return new AdminFacade(new CompaniesDBDAO(), new CouponsDBDAO(), new CustomersDBDAO());
                }
                break;

            case COMPANY:
                if (new CompanyFacade(new CompaniesDBDAO(), new CouponsDBDAO(), new CustomersDBDAO()).login(email, password)) {
                    return new CompanyFacade(new CompaniesDBDAO(), new CouponsDBDAO(), new CustomersDBDAO());
                }
                break;
            case CUSTOMER:
                if (new CustomerFacade(new CompaniesDBDAO(), new CouponsDBDAO(), new CustomersDBDAO()).login(email, password)) {
                    return new CustomerFacade(new CompaniesDBDAO(), new CouponsDBDAO(), new CustomersDBDAO());
                }
                break;
            default: {
                System.out.println("please try again");
                break;
            }

        }

        return null;
    }


}


    }

            }
