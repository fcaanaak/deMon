import styles from "./Navigation.module.css"
import {Link} from "react-router-dom";
import ArmingButton from "./ArmingButton.jsx";

function Navigation() {
    return (
        <nav className={styles.navigationBar}>
            <Link className={styles.navigationElement} to={"/"}>Dashboard</Link>
            <Link className={styles.navigationElement} to={"/reports"}>Reports</Link>
            <Link className={styles.navigationElement} to={"/devices"}>Devices</Link>

        </nav>
    );
}

export default Navigation;