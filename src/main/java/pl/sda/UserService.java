package pl.sda;



public class UserService {


    public static Long registerNewUser(User user) throws Exception {

        String email = user.getEmail();

        if (UserRepository.checkIsUserByEmailExist(email)) {
            throw new DuplicateEmailException(
                    String.format("User with %s email exist", email)
            );
        }

        return UserRepository.saveOrUpdate(user);
    }
}
