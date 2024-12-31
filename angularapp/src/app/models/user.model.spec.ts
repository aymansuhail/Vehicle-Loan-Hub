import { User } from './user.model';

describe('User Model', () => {

  fit('Frontend_User_model_should_create_an_instance', () => {
    // Create a sample User object
    const user: User = {
      email: 'user@example.com',
      password: 'securePassword123',
      username: 'exampleUser',
      mobileNumber: '1234567890',
      userRole: 'admin'
    };

    // Assertion to check if user is truthy (not null or undefined)
    expect(user).toBeTruthy();

    // Assertions to verify specific properties of the user object
    expect(user.userId).toBeUndefined(); // userId is optional, so it should be undefined
    expect(user.email).toBe('user@example.com');
    expect(user.password).toBe('securePassword123');
    expect(user.username).toBe('exampleUser');
    expect(user.mobileNumber).toBe('1234567890');
    expect(user.userRole).toBe('admin');
  });

});
