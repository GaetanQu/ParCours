import { RoleDTO } from "./role.dto";
import { SessionDTO } from "./session.dto";

export interface UserDTO {
    id: number;
    first_name: string;
    last_name: string;
    username: string;
    roles: RoleDTO[];
    sessions: SessionDTO[];
}