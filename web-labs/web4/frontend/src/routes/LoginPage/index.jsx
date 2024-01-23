import Author from "../../components/Sections/Author";
import AppBody from "../../components/AppBody";
import LogIn from "../../components/Sections/LogIn";
import {Toaster} from "react-hot-toast";

function LoginPage() {


    return (
        <AppBody>
            <Author />
            <LogIn />
            <Toaster
                position="bottom-center"
                reverseOrder={false}
            />
        </AppBody>
    );

}

export default LoginPage;
