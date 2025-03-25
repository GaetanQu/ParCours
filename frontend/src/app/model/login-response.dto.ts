import { UserDTO } from "./user.dto";

export interface LoginResponseDTO {
    token: string;
    user: UserDTO;
}