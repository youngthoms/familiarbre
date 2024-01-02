import styled from "styled-components";

export const FormContainer = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;

  .ant-form-item-control-input {
    padding: 4px 11px; // Ajustez cette valeur selon vos besoins pour augmenter le padding
  }

  .ant-input {
    height: auto; // ou une valeur spécifique si vous voulez augmenter la hauteur
  }
// ...
`;