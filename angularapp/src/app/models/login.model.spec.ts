import { Login } from './login.model';

describe('Login Model', () => {

  fit('Frontend_Login_model_should_create_an_instance', () => {
    // Create a sample Login object
    const login: Login = {
      email: 'user@example.com',
      password: 'securePassword123'
    };

    // Assertion to check if login is truthy (not null or undefined)
    expect(login).toBeTruthy();

    // Assertions to verify specific properties of the login object
    expect(login.email).toBe('user@example.com');
    expect(login.password).toBe('securePassword123');
  });

});
