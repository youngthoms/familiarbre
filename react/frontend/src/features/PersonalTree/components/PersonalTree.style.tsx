// StyledComponents.ts
import styled from 'styled-components';
import {Layout} from 'antd';
import {Header} from "antd/lib/layout/layout";

export const StyledSider = styled(Layout.Sider)`
  overflow: auto;
  height: 100vh;
  position: fixed;
  left: 0;
  top: 0;
  bottom: 0;
`;

export const StyledLayout = styled(Layout)`
  &.site-layout {
    margin: 0;
  }
`;

export const StyledHeader = styled(Header)`
  padding: 0;
  background: #ffffff;
`;

export const StyledContent = styled(Layout.Content)`
  margin: 24px 16px 0;
  overflow: initial;
`;

export const ContentContainer = styled.div`
  padding: 24px;
  text-align: center;
  background: #ffffff;
`;
