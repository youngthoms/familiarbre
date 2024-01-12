import styled from "styled-components";
import {Menu} from "antd";

export const StyledMenu = styled(Menu)`
  background-color: #1A757E; // Définit la couleur de fond du menu

  .ant-menu-item {
    color: #fff; // Couleur du texte
    padding-left: 16px!important; // Aligner avec le titre de l'utilisateur
    padding-right: 16px!important; // Aligner avec le titre de l'utilisateur
    margin: 0; // Enlever les marges par défaut si nécessaire
  }


  .ant-menu-item-selected {
    background-color: #1A757E; // Change la couleur de fond pour l'élément sélectionné
  }
`;
